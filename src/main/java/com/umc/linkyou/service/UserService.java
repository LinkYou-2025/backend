package com.umc.linkyou.service;

import com.umc.linkyou.domain.Users;
import com.umc.linkyou.web.dto.EmailVerificationResponse;
import com.umc.linkyou.web.dto.UserRequestDTO;
import com.umc.linkyou.web.dto.UserResponseDTO;

public interface UserService {

    Users joinUser(UserRequestDTO.JoinDTO request);
    UserResponseDTO.LoginResultDTO loginUser(UserRequestDTO.LoginRequestDTO request);

    public void validateNickNameNotDuplicate(String nickname);

    // 이메일 인증
    // 인증 코드 전송
    public void sendCode(String toEmail);

    // 인증 코드 생성
    private String createCode() {
        return null;
    }

    // 인증 코드 검증
    public EmailVerificationResponse verifyCode(String email, String authCode);

}
