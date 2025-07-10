package com.umc.linkyou.web.dto;

import com.umc.linkyou.domain.Category;
import com.umc.linkyou.domain.mapping.LinkuFolder;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class LinkuResponseDTO {
    @Setter
    @Getter
    @Builder
    public static class LinkuResultDTO {
        private Long userId;
        private Long linkuId;
        private Long linkuFolderId;
        private Long categoryId;
        private String linku; //링크
        private String memo;
        private Long emotionId;
        private String domain;
        private String domainImageUrl;
        private String linkuImageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
