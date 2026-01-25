package com.jslog_spring.backlog.interfaces.rest.dto;

import java.time.LocalDateTime;

public record BacklogUpdateRequest(
        String name,
        String desc,
        LocalDateTime dueDate
) {
}
