package com.jslog_spring.global.exception;

public class TokenNotFoundException extends ServiceException {
    public TokenNotFoundException() {
        super(ErrorCode.TOKEN_NOT_FOUND);
    }
}
