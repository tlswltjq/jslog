package com.jslog_spring.domain.todo.dto;

import com.jslog_spring.domain.todo.entity.Todo;

import java.time.LocalDateTime;

public record TodoResponse(
        Long id,
        String category,
        String title,
        String description,
        LocalDateTime createAt
) {
    public TodoResponse (Todo todo){
        this(
                todo.getId(),
                todo.getCategory(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getCreateDate()
        );
    }
}
