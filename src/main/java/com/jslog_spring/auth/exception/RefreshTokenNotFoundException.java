package com.jslog_spring.auth.exception;

import com.jslog_spring.common.error.BusinessException;
import com.jslog_spring.common.error.ErrorCode;

public class RefreshTokenNotFoundException extends BusinessException {
    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
