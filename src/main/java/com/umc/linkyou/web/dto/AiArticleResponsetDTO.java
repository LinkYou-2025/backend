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
        private Linku linku;
        private Situation situation;
        private Emotion emotion;
        private String title;
        private String aiFeeling;
        private String aiCategory;
        private String summary;
        private String imgUrl;
        private String keyword;
    }
}
