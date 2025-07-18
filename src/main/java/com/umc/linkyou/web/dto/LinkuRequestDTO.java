package com.umc.linkyou.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class LinkuRequestDTO {
    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LinkuCreateDTO {
        @Schema(example = "https://draconist.clickn.co.kr/")
        private String linku;

        @Schema(example = "test용 메모입니다.")
        private String memo;

        @Schema(example = "2")
        private Long emotionId;
    }

    @Setter
    @Getter
    @Builder
    public static class LinkuUpdateDTO {

    }

}
