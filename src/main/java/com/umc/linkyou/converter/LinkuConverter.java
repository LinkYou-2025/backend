package com.umc.linkyou.converter;

import com.umc.linkyou.domain.*;
import com.umc.linkyou.domain.classification.Category;
import com.umc.linkyou.domain.classification.Domain;
import com.umc.linkyou.domain.classification.Emotion;
import com.umc.linkyou.domain.mapping.LinkuFolder;
import com.umc.linkyou.domain.mapping.UsersLinku;
import com.umc.linkyou.web.dto.LinkuRequestDTO;
import com.umc.linkyou.web.dto.LinkuResponseDTO;

public class LinkuConverter {
    // Converter: RequestParam으로 받은 데이터 -> LinkuCreateDTO 생성
    public static LinkuRequestDTO.LinkuCreateDTO toLinkuCreateDTO(String linku, String memo, Long emotionId) {
        return LinkuRequestDTO.LinkuCreateDTO.builder()
                .linku(linku)
                .memo(memo)
                .emotionId(emotionId)
                .build();
    }

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
    // UsersLinku 생성
    public static UsersLinku toUsersLinku(Users user, Linku linku, Emotion emotion, String memo, String imageUrl) {
        return UsersLinku.builder()
                .user(user)
                .linku(linku)
                .emotion(emotion)
                .memo(memo)
                .imageUrl(imageUrl)
                .build();
    }

    //LinkuFolder 생성
    public static LinkuFolder toLinkuFolder(Folder folder, UsersLinku usersLinku) {
        return LinkuFolder.builder()
                .folder(folder)
                .usersLinku(usersLinku)
                .build();
    }

    // Linku 생성
    public static Linku toLinku(String linkuUrl, Category category, Domain domain) {
        return Linku.builder()
                .linku(linkuUrl)
                .category(category)
                .domain(domain)
                .build();
    }


}
