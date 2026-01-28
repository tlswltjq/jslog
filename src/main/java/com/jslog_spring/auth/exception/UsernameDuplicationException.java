package com.jslog_spring.auth.exception;

import com.jslog_spring.common.error.BusinessException;
import com.jslog_spring.common.error.ErrorCode;

public class UsernameDuplicationException extends BusinessException {
    public UsernameDuplicationException() {
        super(ErrorCode.USERNAME_DUPLICATION);
    }
}
