package com.umc.linkyou.apiPayload.code.status;

import com.umc.linkyou.apiPayload.code.BaseCode;
import com.umc.linkyou.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    // 생성 관련 응답
    _CREATED(HttpStatus.CREATED, "COMMON201", "성공적으로 생성(저장)되었습니다."),
    // 회원가입 관련 응답
    _VERIFICATION_CODE_SENT(HttpStatus.ACCEPTED, "EMAIL202", "인증 코드 전송 성공."),
    _NICKNAME_AVAILABLE(HttpStatus.ACCEPTED, "NICKNAME202", "닉네임 중복 확인 성공."),
    _EMAIL_VERIFICATION_SUCCESS(HttpStatus.ACCEPTED, "EMAIL202", "이메일 인증 성공.")
    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
