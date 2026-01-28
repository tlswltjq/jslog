package com.jslog_spring.backlog.application.dto;

import java.time.LocalDateTime;

public record BacklogInfo(
        Long id,
        Long ownedBy,
        String name,
        String desc,
        LocalDateTime dueDate,
        Boolean isDone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
