package com.jslog_spring.auth.exception;

import com.jslog_spring.common.error.BusinessException;
import com.jslog_spring.common.error.ErrorCode;

public class AccountNotFoundException extends BusinessException {
    public AccountNotFoundException() {
        super(ErrorCode.ACCOUNT_NOT_FOUND);
    }
}
