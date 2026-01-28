package com.jslog_spring.backlog.exception;

import com.jslog_spring.common.error.BusinessException;
import com.jslog_spring.common.error.ErrorCode;

public class BacklogOwnerMismatchException extends BusinessException {
    public BacklogOwnerMismatchException() {
        super(ErrorCode.BACKLOG_OWNER_MISMATCH);
    }
}
