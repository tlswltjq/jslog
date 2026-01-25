package com.jslog_spring.backlog.exception;

import com.jslog_spring.common.error.BusinessException;
import com.jslog_spring.common.error.ErrorCode;

public class BacklogNotFoundException extends BusinessException {
    public BacklogNotFoundException() {
        super(ErrorCode.BACKLOG_NOT_FOUND);
    }
}
