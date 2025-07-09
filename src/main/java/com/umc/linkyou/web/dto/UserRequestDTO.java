package com.umc.linkyou.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserRequestDTO {

    @Getter
    @Setter
    public static class JoinDTO {

        @Schema(example = "별명")
        @NotBlank
        String nickName;

        @Schema(example = "example@gmail.com")
        @NotBlank
        @Email
        String email;

        @Schema(example = "zaq123")
        @NotBlank
        String password;

        @Schema(example = "0")
        @NotNull
        Integer gender;

        @Schema(example = "0")
        @NotNull
        Integer job;

        @Schema(example = "[\"CAREER\", \"STUDY\"]")
        List<String> purposeList;

        @Schema(example = "[\"IT\", \"DESIGN\"]")
        List<String> interestList;
    }

    @Getter
    @Setter
    public static class LoginRequestDTO {
        @Schema(example = "example@gmail.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        private String email;

        @Schema(example = "zaq123")
        @NotBlank(message = "패스워드는 필수입니다.")
        private String password;
    }
}
