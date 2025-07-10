package com.umc.linkyou.converter;

import com.umc.linkyou.domain.Linku;
import com.umc.linkyou.web.dto.LinkuResponseDTO;

public class LinkuConverter {
    // Linku -> LinkuIsExistDTO 변환
    public static LinkuResponseDTO.LinkuIsExistDTO toLinkuIsExistDTO(Long userId, Linku linku) {
        if (linku == null) {
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
                .linkuId(linku.getLinkuId())
                .memo(linku.getMemo())
                .emotionId(linku.getEmotion().getEmotionId())
                .createdAt(linku.getCreatedAt())
                .updatedAt(linku.getUpdatedAt())
                .build();
    }

}
