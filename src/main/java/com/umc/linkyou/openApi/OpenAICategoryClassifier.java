package com.umc.linkyou.openApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class OpenAICategoryClassifier {

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    private final ObjectMapper objectMapper;
    private final WebContentExtractor webContentExtractor;

    /**
     * URL 본문을 크롤링해서 카테고리 중 하나로 분류, 실패 시 null 반환
     */
    public Long classifyCategoryByUrl(String url, List<?> categories) {
        try {
            String pageContent = webContentExtractor.extractTextFromUrl(url);
            if (pageContent == null || pageContent.isBlank()) {
                log.warn("[카테고리 분류 실패] 본문 추출 실패 URL: {}", url);
                return null;
            }

            if (pageContent.length() > 2800)
                pageContent = pageContent.substring(0, 2800); // 토큰 제한 대비 자름

            String categoryList = categories.stream()
                    .map(c -> {
                        var entity = (com.umc.linkyou.domain.classification.Category) c;
                        return "- id: " + entity.getCategoryId() + ", name: \"" + entity.getCategoryName() + "\"";
                    })
                    .collect(Collectors.joining("\n"));

            String prompt = String.format("""
                다음 웹페이지 본문을 기반으로 제공된 카테고리 목록에서 가장 적절한 카테고리 하나만 선택하고 ID를 숫자로만 답하세요.

                📄 본문:
                %s

                카테고리 목록:
                %s

                ✅ JSON 형식으로 출력:
                {"categoryId": 숫자}

                ⚠ JSON 외 다른 내용 없이 출력하세요.
                ⚠ 모든 응답은 한국어로 자연스럽게 작성해주세요.
                """, pageContent, categoryList);

            Map<String, Object> requestBody = Map.of(
                    "model", model,
                    "messages", List.of(
                            Map.of("role", "system", "content", "당신은 웹페이지 내용을 분석하여 가장 적절한 카테고리 ID 숫자만 반환하는 AI 입니다."),
                            Map.of("role", "user", "content", prompt)
                    ),
                    "temperature", 0.3
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);

            log.info("[OpenAI 요청 시작 - 카테고리 분류]");
            log.debug("[OpenAI 프롬프트]:\n{}", prompt);

            ResponseEntity<JsonNode> response = new RestTemplate().postForEntity(apiUrl, request, JsonNode.class);

            String rawContent = response.getBody()
                    .path("choices").get(0)
                    .path("message").path("content")
                    .asText();

            log.info("[OpenAI 카테고리 분류 응답]: {}", rawContent);

            // JSON 추출 (중괄호 범위만)
            int startIdx = rawContent.indexOf("{");
            int endIdx = rawContent.lastIndexOf("}");
            if (startIdx == -1 || endIdx == -1 || startIdx >= endIdx) {
                log.warn("[OpenAI 응답 파싱 실패] JSON 범위 없거나 잘못됨: {}", rawContent);
                return null; // 실패시 null
            }

            String jsonString = rawContent.substring(startIdx, endIdx + 1);
            CategoryResponse categoryResponse = objectMapper.readValue(jsonString, CategoryResponse.class);
            return categoryResponse.getCategoryId();

        } catch (Exception e) {
            log.error("[카테고리 분류 에러]", e);
            return null;
        }
    }

    // 내부 DTO 클래스
    private static class CategoryResponse {
        private Long categoryId;
        public Long getCategoryId() { return categoryId; }
        public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    }
}
