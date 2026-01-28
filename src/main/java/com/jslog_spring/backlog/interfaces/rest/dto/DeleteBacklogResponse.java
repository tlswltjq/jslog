package com.jslog_spring.backlog.interfaces.rest.dto;

public record DeleteBacklogResponse(
        Long backlogId
) {
    public static DeleteBacklogResponse from(Long backlogId) {
        return new DeleteBacklogResponse(backlogId);
    }
}
