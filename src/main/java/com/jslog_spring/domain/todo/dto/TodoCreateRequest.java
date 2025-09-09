package com.jslog_spring.domain.todo.dto;

public record TodoCreateRequest(
        String category,
        String title,
        String description
) {
}
