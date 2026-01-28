package com.jslog_spring.backlog.interfaces.rest.dto;

public record UndoneBacklogResponse(
        Long backlogId
) {
    public static UndoneBacklogResponse from(Long backlogId) {
        return new UndoneBacklogResponse(backlogId);
    }
}
