package com.jslog_spring.backlog.interfaces.rest.dto;

import com.jslog_spring.backlog.application.dto.BacklogInfo;

import java.time.LocalDateTime;

public record BacklogResponse(
        Long id,
        Long ownedBy,
        String name,
        String desc,
        LocalDateTime dueDate,
        Boolean isDone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    public static BacklogResponse from(BacklogInfo info) {
        return new BacklogResponse(
                info.id(),
                info.ownedBy(),
                info.name(),
                info.desc(),
                info.dueDate(),
                info.isDone(),
                info.createdAt(),
                info.updatedAt());
    }
}
