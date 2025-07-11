package com.umc.linkyou.converter;

import com.umc.linkyou.domain.Category;
import com.umc.linkyou.domain.Domain;
import com.umc.linkyou.domain.Emotion;
import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.domain.mapping.LinkuFolder;
import com.umc.linkyou.domain.mapping.UsersLinku;
import com.umc.linkyou.web.dto.LinkuResponseDTO;

public class LinkuConverter {
    // Linku생성 → LinkuResultDTO 변환
    public static LinkuResponseDTO.LinkuResultDTO toLinkuResultDTO(
            Long userId,
            Linku linku,
            UsersLinku usersLinku,
            LinkuFolder linkuFolder,
            Category category,
            Emotion emotion,
            Domain domain
    ) {
        return LinkuResponseDTO.LinkuResultDTO.builder()
                .userId(userId)
                .linkuId(linku.getLinkuId())
                .linkuFolderId(linkuFolder.getLinkuFolderId())
                .categoryId(category.getCategoryId())
                .linku(linku.getLinku())
                .memo(usersLinku.getMemo())
                .emotionId(usersLinku.getEmotion().getEmotionId())
                .domain(domain.getName())
                .domainImageUrl(domain.getImageUrl())
                .linkuImageUrl(usersLinku.getImageUrl())
                .createdAt(linku.getCreatedAt())
                .updatedAt(linku.getUpdatedAt())
                .build();
    }


    // Linku -> LinkuIsExistDTO 변환
    public static LinkuResponseDTO.LinkuIsExistDTO toLinkuIsExistDTO(Long userId, UsersLinku usersLinku) {
        if (usersLinku == null) {
            return LinkuResponseDTO.LinkuIsExistDTO.builder()
                    .isExist(false)
                    .userId(userId)
                    .linkuId(null)
                    .memo(null)
                    .emotionId(null)
                    .createdAt(null)
                    .updatedAt(null)
                    .build();
        }
        return LinkuResponseDTO.LinkuIsExistDTO.builder()
                .isExist(true)
                .userId(userId)
                .linkuId(usersLinku.getLinku().getLinkuId())
                .memo(usersLinku.getMemo())
                .emotionId(usersLinku.getEmotion().getEmotionId())
                .createdAt(usersLinku.getLinku().getCreatedAt())
                .updatedAt(usersLinku.getLinku().getUpdatedAt())
                .build();
    }


}
