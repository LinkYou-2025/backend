package com.umc.linkyou.service.curation.gpt;

import com.umc.linkyou.web.dto.curation.GptMentResponse;
import com.umc.linkyou.web.dto.curation.RecommendedLinkResponse;

import java.util.List;

public interface GptService {
    GptMentResponse generateMent(String emotionName);
    List<RecommendedLinkResponse> generateExternalRecommendationsFromContext(
            List<String> recentUrls, List<String> tagNames, int limit
    );
}