package com.umc.linkyou.web.controller;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.apiPayload.code.status.SuccessStatus;
import com.umc.linkyou.apiPayload.exception.handler.UserHandler;
import com.umc.linkyou.converter.UserConverter;
import com.umc.linkyou.domain.Users;
import com.umc.linkyou.service.UserService;
import com.umc.linkyou.web.dto.EmailVerificationResponse;
import com.umc.linkyou.web.dto.UserRequestDTO;
import com.umc.linkyou.web.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/join")
    public ApiResponse<UserResponseDTO.JoinResultDTO> join(@RequestBody @Valid UserRequestDTO.JoinDTO request){
        Users user = userService.joinUser(request);
        return ApiResponse.onSuccess(UserConverter.toJoinResultDTO(user));
    }

    // 로그인
    @PostMapping("/login")
    @Operation(summary = "유저 로그인 API",description = "유저가 로그인하는 API입니다.")
    public ApiResponse<UserResponseDTO.LoginResultDTO> login(@RequestBody @Valid UserRequestDTO.LoginRequestDTO request) {
        return ApiResponse.onSuccess(userService.loginUser(request));
    }

    // 닉네임 중복확인
    @GetMapping("/check-nickname")
    public ApiResponse<String> checkNickname(@RequestParam String nickname) {
        userService.validateNickNameNotDuplicate(nickname);
        return ApiResponse.of(SuccessStatus._NICKNAME_AVAILABLE, "사용 가능한 닉네임 입니다.");
    }

    // 이메일 인증 코드 전송
    @PostMapping("emails/code")
    public ApiResponse<String> sendCode(@RequestParam("email") @Valid String email) {
        userService.sendCode(email);
        return ApiResponse.of(SuccessStatus._VERIFICATION_CODE_SENT, "이메일로 인증 코드가 전송되었습니다.");
    }

    // 이메일 인증 코드 검증
    @GetMapping("/emails/verify")
    public ApiResponse<EmailVerificationResponse> verifyCode(@RequestParam("email") @Valid String email,
                                                             @RequestParam("code") String authCode) {
        EmailVerificationResponse response = userService.verifyCode(email, authCode);
        return ApiResponse.of(SuccessStatus._EMAIL_VERIFICATION_SUCCESS, response);
    }
}
