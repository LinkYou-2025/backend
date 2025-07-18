package com.umc.linkyou.converter;

import com.umc.linkyou.domain.AiArticle;
import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.domain.classification.Category;
import com.umc.linkyou.domain.classification.Emotion;
import com.umc.linkyou.domain.classification.Situation;
import com.umc.linkyou.domain.mapping.UsersLinku;
import com.umc.linkyou.openApi.SummaryAnalysisResultDTO;
import com.umc.linkyou.web.dto.AiArticleResponsetDTO;

public class AiArticleConverter {
    // 엔티티 생성 메서드
    public static AiArticle toEntity(SummaryAnalysisResultDTO result,
                                     Situation selectedSituation,
                                     Emotion selectedEmotion,
                                     Category selectedCategory,
                                     UsersLinku usersLinku) {
        return AiArticle.builder()
                .title(result.getTitle())
                .summary(result.getSummary())
                .aiFeeling(selectedEmotion.getName())
                .aiCategory(selectedCategory.getCategoryName())
                .situation(selectedSituation)
                .usersLinku(usersLinku)
                .imgUrl(usersLinku.getImageUrl())
                .keyword(result.getKeywords())
                .build();
    }

    // DTO 생성 메서드
    public static AiArticleResponsetDTO.AiArticleResultDTO toDto(
            AiArticle saved,
            Linku linku,
            UsersLinku usersLinku,
            Situation selectedSituation,
            Emotion selectedEmotion,
            Category selectedCategory
    ) {
        return AiArticleResponsetDTO.AiArticleResultDTO.builder()
                .id(saved.getId())
                .linkuId(linku.getLinkuId())
                .situationId(selectedSituation.getId())
                .situationName(selectedSituation.getName())
                .emotionId(selectedEmotion.getEmotionId())
                .emotionName(selectedEmotion.getName())
                .title(saved.getTitle())
                .aiFeeling(saved.getAiFeeling())
                .aiCategoryId(selectedCategory.getCategoryId())
                .categoryName(selectedCategory.getCategoryName())
                .summary(saved.getSummary())
                .imgUrl(saved.getImgUrl())
                .memo(usersLinku.getMemo())
                .keyword(saved.getKeyword())
                .build();
    }
}
