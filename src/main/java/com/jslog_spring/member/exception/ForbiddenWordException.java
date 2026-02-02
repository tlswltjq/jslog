package com.jslog_spring.member.exception;

import com.jslog_spring.common.error.BusinessException;
import com.jslog_spring.common.error.ErrorCode;

public class ForbiddenWordException extends BusinessException {
    public ForbiddenWordException() {
        super(ErrorCode.FORBIDDEN_WORD_USED);
    }
}
