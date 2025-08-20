package com.jslog_spring.domain.member.exception;

import com.jslog_spring.global.exception.ErrorCode;
import com.jslog_spring.global.exception.ServiceException;

public class InvalidInputValueException extends ServiceException {
    public InvalidInputValueException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
