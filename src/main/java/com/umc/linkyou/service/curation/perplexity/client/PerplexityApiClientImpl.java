package com.umc.linkyou.service.curation.perplexity.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.timeout.TimeoutException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PerplexityApiClientImpl implements PerplexityApiClient {

    @Value("${perplexity.api.key}")
    private String apiKey;

    @Value("${perplexity.api.url}")
    private String baseUrl;

    // 온라인(웹서치) 모델
    @Value("${perplexity.model}")
    private String model;

    @Value("${perplexity.temperature}")
    private double temperature;

    @Value("${perplexity.max-tokens}")
    private int maxTokens;

    @Value("${perplexity.http-timeout-ms}")
    private int httpTimeoutMs;

    private final ObjectMapper objectMapper;

    private WebClient webClient;

    @PostConstruct
    void init() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Math.min(httpTimeoutMs, 8000)) // 연결 타임아웃
                .responseTimeout(Duration.ofMillis(httpTimeoutMs))                           // 응답 타임아웃
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(httpTimeoutMs / 1000))
                        .addHandlerLast(new WriteTimeoutHandler(httpTimeoutMs / 1000))
                );

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Override
    public String chat(List<Map<String, String>> messages) {
        Map<String, Object> body = Map.of(
                "model", model,
                "temperature", temperature,
                "max_tokens", maxTokens,
                "messages", messages
        );

        long started = System.currentTimeMillis();

        String raw = null;
        try {
            raw = webClient.post()
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(
                            s -> s.is4xxClientError() || s.is5xxServerError(),
                            resp -> resp.bodyToMono(String.class).flatMap(errBody -> {
                                System.err.println("[Perplexity] HTTP " + resp.statusCode());
                                System.err.println("[Perplexity] ERROR BODY:\n" + errBody);
                                return reactor.core.publisher.Mono.error(
                                        new RuntimeException("Perplexity API error: " + resp.statusCode() + " " + errBody)
                                );
                            })
                    )
                    // 🔁 429/5xx/Timeout 시 2회 백오프 재시도
                    .bodyToMono(String.class)
                    .retryWhen(
                            Retry.backoff(2, Duration.ofMillis(400))
                                    .filter(ex ->
                                            ex instanceof TimeoutException ||
                                                    (ex instanceof WebClientResponseException wex &&
                                                            (wex.getStatusCode().is5xxServerError() ||
                                                                    wex.getStatusCode().value() == 429)))
                                    .maxBackoff(Duration.ofSeconds(3))
                    )
                    // 최종 단일 타임아웃 (Netty timeout과 중복되면 살짝만 길게)
                    .timeout(Duration.ofMillis(httpTimeoutMs + 2000))
                    .block();
        } catch (TimeoutException te) {
            System.err.println("[Perplexity] Timeout (" + httpTimeoutMs + "ms) — prompt이 길거나 네트워크 지연");
            return null; // 서비스에서 폴백 처리
        } catch (Exception e) {
            System.err.println("[Perplexity] Exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            return null;
        } finally {
            System.out.println("[Perplexity] elapsed=" + (System.currentTimeMillis() - started) + "ms");
        }

        if (raw == null) return null;

        try {
            JsonNode root = objectMapper.readTree(raw);
            JsonNode choices = root.path("choices");
            if (!choices.isArray() || choices.isEmpty()) {
                System.err.println("❌ Perplexity choices empty. RAW:\n" + raw);
                return null;
            }
            String content = choices.get(0).path("message").path("content").asText(null);
            if (content == null || content.isBlank()) {
                System.err.println("❌ Perplexity content null/blank. RAW:\n" + raw);
                return null;
            }
            return content;
        } catch (Exception e) {
            System.err.println("❌ Perplexity 응답 파싱 실패: " + e.getMessage() + "\nRAW:\n" + raw);
            return null;
        }
    }
}
