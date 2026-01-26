package com.jslog_spring.member.exception;

import com.jslog_spring.common.error.BusinessException;
import com.jslog_spring.common.error.ErrorCode;

public class NicknameDuplicationException extends BusinessException {
    public NicknameDuplicationException() {
        super(ErrorCode.NICKNAME_DUPLICATION);
    }
}
