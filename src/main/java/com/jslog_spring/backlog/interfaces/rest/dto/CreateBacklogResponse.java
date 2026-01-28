package com.jslog_spring.backlog.interfaces.rest.dto;

public record CreateBacklogResponse(
        Long backlogId
) {
    public static CreateBacklogResponse from(Long backlogId) {
        return new CreateBacklogResponse(backlogId);
    }
}
