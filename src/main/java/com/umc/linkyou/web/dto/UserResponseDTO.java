package com.umc.linkyou.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO{
        Long userId;

        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO{
        Long userId;

        String accessToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDTO{
        String nickname;

        Long myLinku; // 나의 링크

        Long myFolder; // 나의 폴더

        // 내가 만든 ai 링크
        Long myAiLinku;

    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class withDrawalResultDTO{
        Long userId;
        String nickname;
        LocalDateTime createdAt;
        String status;
        LocalDateTime inactiveDate;
    }

}
