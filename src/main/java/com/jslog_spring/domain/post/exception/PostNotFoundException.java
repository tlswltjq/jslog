package com.jslog_spring.domain.post.exception;

import com.jslog_spring.global.exception.ErrorCode;
import com.jslog_spring.global.exception.ServiceException;

public class PostNotFoundException extends ServiceException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
