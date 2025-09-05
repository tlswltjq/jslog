package com.jslog_spring.domain.todo.exception;

import com.jslog_spring.global.exception.ErrorCode;
import com.jslog_spring.global.exception.ServiceException;
import lombok.Getter;

@Getter
public class TodoOwnershipException extends ServiceException {

    private final Long todoId;
    private final Long memberId;

    public TodoOwnershipException(Long todoId, Long memberId) {
        super(ErrorCode.TODO_ACCESS_DENIED);
        this.todoId = todoId;
        this.memberId = memberId;
    }
}
