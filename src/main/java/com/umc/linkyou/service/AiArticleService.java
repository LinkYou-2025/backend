package com.umc.linkyou.service;

import com.umc.linkyou.web.dto.AiArticleResponsetDTO;

public interface AiArticleService {
    AiArticleResponsetDTO.AiArticleResultDTO saveAiArticle(Long linkuId, Long userId);
}
