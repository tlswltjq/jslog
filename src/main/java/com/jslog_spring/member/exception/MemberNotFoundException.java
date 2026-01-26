package com.jslog_spring.member.exception;

import com.jslog_spring.common.error.BusinessException;
import com.jslog_spring.common.error.ErrorCode;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
