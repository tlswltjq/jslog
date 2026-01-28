package com.jslog_spring.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE("C001", "Invalid Input Value", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED("C002", "Method Not Allowed", HttpStatus.METHOD_NOT_ALLOWED),
    INTERNAL_SERVER_ERROR("C003", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),

    // Member
    MEMBER_NOT_FOUND("M001", "사용자를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    NICKNAME_DUPLICATION("M002", "이미 존재하는 닉네임입니다", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATION("M003", "이미 존재하는 이메일입니다", HttpStatus.BAD_REQUEST),

    // Backlog
    BACKLOG_NOT_FOUND("B001", "백로그를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    BACKLOG_OWNER_MISMATCH("B002", "이 백로그의 소유자가 아닙니다", HttpStatus.FORBIDDEN),

    // Auth
    ACCOUNT_NOT_FOUND("A001", "계정을 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_NOT_FOUND("A002", "리프레시 토큰을 찾을 수 없습니다", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
