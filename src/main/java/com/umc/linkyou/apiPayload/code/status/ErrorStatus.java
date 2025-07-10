package com.umc.linkyou.apiPayload.code.status;

import com.umc.linkyou.apiPayload.code.BaseErrorCode;
import com.umc.linkyou.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _EXPIRED_VERIFICATION_CODE(HttpStatus.BAD_REQUEST,"COMMON400","인증 코드가 만료되었습니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "COMMON4011", "잘못된 토큰입니다."),
    _INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "COMMON4012", "잘못된 비밀번호입니다."),

    // 로그인 회원가입 에러
    _DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "COMMON403", "중복된 닉네임입니다."),
    _DUPLICATE_JOIN_REQUEST(HttpStatus.CONFLICT, "COMMON403", "중복된 이메일입니다."),
    _VERIFICATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON404", "인증 코드 검증 실패"),
    _USER_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "사용자를 찾을 수 없습니다."),
    _PURPOSE_NOT_PROVIDED(HttpStatus.NOT_FOUND, "COMMON404", "목적을 선택해야합니다."),
    _INTEREST_NOT_PROVIDED(HttpStatus.NOT_FOUND, "COMMON404", "관심 분야를 선택해야합니다."),
    _NO_SUCH_ALGORITHM(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "인증 코드 생성 실패"),
    _SEND_MAIL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "인증 코드 전송 실패")
    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
