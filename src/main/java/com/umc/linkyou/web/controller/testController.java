package com.umc.linkyou.web.controller;

import com.umc.linkyou.apiPayload.ApiResponse;
import com.umc.linkyou.apiPayload.code.status.ErrorStatus;
import com.umc.linkyou.config.security.jwt.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    @Operation(
            summary = "accessToken 인증 테스트",
            description = "Authorization 헤더에 accessToken을 담아 호출하면 인증된 사용자 정보를 반환합니다."
    )
    public ApiResponse<TestUserInfoResponse> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            // 토큰이 없거나 잘못된 경우
            return ApiResponse.onFailure(ErrorStatus._INVALID_TOKEN.getCode(), ErrorStatus._INVALID_TOKEN.getMessage(), null);
        }

        Long userId = userDetails.getUsers().getId().longValue();
        String email = userDetails.getEmail();

        return ApiResponse.onSuccess(new TestUserInfoResponse(userId, email));
    }

    // 응답 DTO (record 사용)
    public record TestUserInfoResponse(Long userId, String email) {}

}
