package com.jslog_spring.member.exception;

import com.jslog_spring.common.error.BusinessException;
import com.jslog_spring.common.error.ErrorCode;

public class BioLengthExceededException extends BusinessException {
    public BioLengthExceededException() {
        super(ErrorCode.BIO_LENGTH_EXCEEDED);
    }
}
