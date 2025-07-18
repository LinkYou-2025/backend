package com.umc.linkyou.web.dto;

import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.domain.classification.Emotion;
import com.umc.linkyou.domain.classification.Situation;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AiArticleResponsetDTO {
    @Setter
    @Getter
    @Builder
    public static class AiArticleResultDTO {
        private Long id;
        private Long linkuId;
        private Long situationId;
        private String situationName;
        private Long emotionId;
        private String emotionName;
        private String title;
        private String aiFeeling;
        private Long aiCategoryId;
        private String categoryName;
        private String summary;
        private String imgUrl;
        private String memo;
        private String keyword;
    }
}
