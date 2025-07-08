package com.umc.linkyou.web.dto;

import com.umc.linkyou.domain.Category;
import com.umc.linkyou.domain.enums.Emotion;
import com.umc.linkyou.domain.mapping.LinkuFolder;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class LinkuRequestDTO {
    @Setter
    @Getter
    @Builder
    public static class LinkuCreateDTO {
        private String linku;
        private String memo;
        private String emotion;
    }

}
