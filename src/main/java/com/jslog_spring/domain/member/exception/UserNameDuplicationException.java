package com.jslog_spring.domain.member.exception;

import com.jslog_spring.global.exception.ErrorCode;
import com.jslog_spring.global.exception.ServiceException;

public class UserNameDuplicationException extends ServiceException {
    public UserNameDuplicationException() {
        super(ErrorCode.USERNAME_DUPLICATION);
    }
}
