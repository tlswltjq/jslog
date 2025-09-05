package com.jslog_spring.domain.todo.exception;

import com.jslog_spring.global.exception.ErrorCode;
import com.jslog_spring.global.exception.ServiceException;
import lombok.Getter;

@Getter
public class TodoNotFoundException extends ServiceException {
    private final Long todoId;

    public TodoNotFoundException(Long id) {
        super(ErrorCode.TODO_NOT_FOUND);
        this.todoId = id;
    }
}
