package com.umc.linkyou.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class DomainDTO {

    // DomainDTO.DomainRequestDTO
    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DomainRequestDTO {
        @Schema(example = "1", description = "도메인 id (수정 시 반드시 필요)")
        private Long id;

        @Schema(example = "NAVER", description = "도메인 명 바꾸기")
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
