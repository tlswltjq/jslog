package com.jslog_spring.backlog.interfaces.rest.dto;

public record UpdateBacklogResponse(
        Long backlogId
) {
    public static UpdateBacklogResponse from(Long backlogId) {
        return new UpdateBacklogResponse(backlogId);
    }
}
