package com.jslog_spring.backlog.interfaces.rest.dto;

public record DoneBacklogResponse(
        Long backlogId
) {
    public static DoneBacklogResponse from(Long backlogId) {
        return new DoneBacklogResponse(backlogId);
    }
}
