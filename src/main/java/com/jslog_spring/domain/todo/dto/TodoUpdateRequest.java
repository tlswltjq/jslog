package com.jslog_spring.domain.todo.dto;

public record TodoUpdateRequest(
        Long id,
        String category,
        String title,
        String description
) {
}
