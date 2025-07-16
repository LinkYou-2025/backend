package com.umc.linkyou.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class DomainDTO {

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DomainRequestDTO {
        @Schema(example = "NAVER", description = "도메인 이름, 수정api는 도메인명을 기준으로 수정할 행을 찾습니다.")
        private String name;

        @Schema(example = "naver.com", description = "도메인 값 (ex: URL 호스트)")
        private String domainTail;
    }

    @Setter
    @Getter
    @Builder
    public static class DomainReponseDTO {
        private String name;
        private String domainTail;
        private String imageUrl;
    }
    
}
