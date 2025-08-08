package com.umc.linkyou.service.curation.gpt.client;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OpenAiApiClientImpl implements OpenAiApiClient {

    @Value("${openai.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public String callGpt(String prompt) {
        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "messages", new Object[] {
                        Map.of("role", "user", "content", prompt)
                },
                "temperature", 0.8
        );

        String rawResponse = webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractContentFromResponse(rawResponse);
    }

    private String extractContentFromResponse(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            return root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText(null);
        } catch (Exception e) {
            System.out.println("❌ GPT 응답 파싱 실패: " + e.getMessage());
            return null;
        }
    }
}