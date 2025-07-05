package com.umc.linkyou.web.dto;

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

        @NotBlank
        String nickName;
        @NotBlank
        @Email
        String email;
        @NotBlank
        String password;
        @NotNull
        Integer gender;
        @NotNull
        Integer job;

        List<String> purposeList;

        List<String> interestList;
    }

    @Getter
    @Setter
    public static class LoginRequestDTO {
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        private String email;

        @NotBlank(message = "패스워드는 필수입니다.")
        private String password;

    }
}
