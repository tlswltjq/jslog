package com.jslog_spring.domain.member.exception;

import com.jslog_spring.global.exception.ErrorCode;
import com.jslog_spring.global.exception.ServiceException;

public class MemberNotFoundException extends ServiceException {
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
