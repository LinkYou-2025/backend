package com.umc.linkyou.service.curation.gpt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.linkyou.service.curation.gpt.client.OpenAiApiClient;
import com.umc.linkyou.web.dto.curation.GptMentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            String rawJson = openAiApiClient.callGpt(prompt);  // → content만 반환됨 (아직 백틱 있음 가능성 有)

            System.out.println("✅ GPT 응답 원문: " + rawJson);

            // 백틱 블록 제거
            String cleaned = rawJson.replaceAll("(?s)```json\\s*", "")
                    .replaceAll("(?s)```", "")
                    .trim();

            System.out.println("✅ 정제된 응답: " + cleaned);

            return objectMapper.readValue(cleaned, GptMentResponse.class);
        } catch (Exception e) {
            System.out.println("❌ GPT 응답 파싱 실패: " + e.getMessage());
            return null;
        }
    }
}
