package com.umc.linkyou.service.curation.gpt;

import com.umc.linkyou.web.dto.curation.GptMentResponse;

public interface GptService {
    GptMentResponse generateMent(String emotionName);
}