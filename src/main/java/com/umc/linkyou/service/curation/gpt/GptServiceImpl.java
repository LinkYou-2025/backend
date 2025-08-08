package com.umc.linkyou.service.curation.gpt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.linkyou.service.curation.gpt.client.OpenAiApiClient;
import com.umc.linkyou.web.dto.curation.GptMentResponse;
import com.umc.linkyou.web.dto.curation.RecommendedLinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GptServiceImpl implements GptService {

    private final OpenAiApiClient openAiApiClient;
    private final ObjectMapper objectMapper;

    @Override
    public GptMentResponse generateMent(String emotionName) {
        String prompt = String.format(
                "당신은 감정 기반 콘텐츠 추천 서비스의 큐레이션 에디터입니다.\n" +
                        "사용자의 현재 감정은 '%s'입니다.\n" +
                        "이 감정을 기반으로, 해당 사용자에게 맞는 콘텐츠를 소개하는 큐레이션 멘트를 작성해주세요.\n\n" +
                        "[큐레이션 멘트 설명]\n" +
                        "- 상단 멘트는 큐레이션 페이지 가장 위에 노출되어, 사용자의 감정에 공감하며 관심을 끌어야 합니다.\n" +
                        "- 하단 멘트는 큐레이션 페이지 마지막에 노출되며, 콘텐츠를 마무리하면서 위로나 응원을 담아야 합니다.\n\n" +
                        "[작성 규칙]\n" +
                        "- 각 멘트는 반드시 한 문장으로 작성하세요.\n" +
                        "- 반드시 \"(닉네임)\"이라는 텍스트를 포함하세요. 이 표현은 절대로 바꾸지 마세요.\n" +
                        "- 아래 형식의 JSON 형태로만 출력하세요:\n" +
                        "{\n" +
                        "  \"header\": \"(닉네임)님, ...\",\n" +
                        "  \"footer\": \"(닉네임)님, ...\"\n" +
                        "}\n\n" +
                        "※ JSON 외에는 아무것도 출력하지 말고, (닉네임)이라는 문자열을 절대로 수정하지 마세요.",
                emotionName
        );

        try {
            String rawJson = openAiApiClient.callGpt(prompt);
            String cleaned = extractJsonObject(rawJson);

            return objectMapper.readValue(cleaned, GptMentResponse.class);
        } catch (Exception e) {
            System.out.println("❌ GPT 멘트 파싱 실패: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<RecommendedLinkResponse> generateExternalRecommendationsFromContext(
            List<String> recentUrls, List<String> tagNames, int limit) {

        String prompt = String.format(
                "아래는 사용자가 최근 저장한 콘텐츠 링크입니다:\n%s\n\n" +
                        "또한, 다음은 사용자에게 중요한 태그입니다: %s\n\n" +
                        "이 정보를 기반으로, 사용자가 아직 보지 않은 새로운 웹 링크를 추천해주세요.\n" +
                        "※ 주의: 위에 제공된 링크(URL)는 절대 다시 사용하지 마세요.\n" +
                        "※ 반드시 실제 존재하는 URL을 제공해주세요. URL은 반드시 실제 존재하는 사이트만 사용하고, 일반적으로 접근 가능한 공개 콘텐츠만 사용하세요.\n\n" +
                        "결과는 아래와 같은 JSON 배열 형식으로 출력해주세요. (최대 %d개)\n\n" +
                        "아무 설명도 달지 말고 JSON 배열만 출력해 주세요." +
                        "[\n" +
                        "  {\"title\": \"...\", \"url\": \"...\"},\n" +
                        "  ... 최대 %d개까지\n" +
                        "]",
                String.join("\n", recentUrls),
                String.join(", ", tagNames),
                limit,
                limit
        );

        try {
            String rawJson = openAiApiClient.callGpt(prompt);
            String cleaned = extractJsonArray(rawJson);

            List<Map<String, String>> parsed = objectMapper.readValue(cleaned, new TypeReference<>() {});

            return parsed.stream()
                    .map(item -> RecommendedLinkResponse.builder()
                            .title(item.get("title"))
                            .url(item.get("url"))
                            .domain(null)      // 후처리로 채울 것
                            .imageUrl(null)    // 후처리로 채울 것
                            .userLinkuId(null) // 내부 추천이 아님
                            .build())
                    .toList();

        } catch (Exception e) {
            System.out.println("❌ GPT 추천 파싱 실패: " + e.getMessage());
            return List.of();
        }
    }

    // ------------------------------
    // 🔧 JSON 정제 유틸
    // ------------------------------
    private String extractJsonObject(String response) {
        if (response == null) return "{}";

        String noBackticks = response.replaceAll("(?s)```json\\s*", "")
                .replaceAll("(?s)```", "")
                .trim();

        int objStart = noBackticks.indexOf("{");
        int objEnd = noBackticks.lastIndexOf("}") + 1;

        return (objStart != -1 && objEnd > objStart)
                ? noBackticks.substring(objStart, objEnd)
                : "{}";
    }

    private String extractJsonArray(String response) {
        System.out.println("🧾 GPT 응답 원문:\n" + response);

        if (response == null) return "[]";

        // 백틱과 "```json" 같은 마크다운 문법만 제거, 본문은 유지
        String cleaned = response.replaceAll("(?s)```json", "")
                .replaceAll("(?s)```", "")
                .trim();

        int start = cleaned.indexOf("[");
        int end = cleaned.lastIndexOf("]") + 1;

        if (start == -1 || end <= start) {
            System.out.println("❌ JSON 배열 포맷이 아님");
            return "[]";
        }

        return cleaned.substring(start, end);
    }

}
