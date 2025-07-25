package com.umc.linkyou.web.dto.alarm;

import lombok.*;

public class AlarmRequestDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AlarmFcmTokenDTO {
        private String fcmToken;
    }
}
