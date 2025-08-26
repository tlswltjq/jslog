package com.jslog_spring.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
//    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", " Invalid Input Value"),
//    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", " Method Not Allowed"),
//    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C003", " Entity Not Found"),
//    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "Server Error"),
//    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C005", " Invalid Type Value"),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "Member not found"),
    USERNAME_DUPLICATION(HttpStatus.CONFLICT, "M002", "Username is Duplication"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "M003", "Invalid input value"),

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "Post not found"),
//    UNAUTHORIZED_POST_ACCESS(HttpStatus.FORBIDDEN, "P002", "Unauthorized post access");

    // JWT
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A001", "Invalid token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A002", "Expired token");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
