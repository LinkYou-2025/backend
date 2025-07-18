package com.umc.linkyou.web.dto.curation;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateCurationResponse {
    private Long curationId;
    private String month;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
}