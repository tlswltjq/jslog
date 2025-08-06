package com.jslog_spring.domain.post.dto;

public record PostResponseDto(
    Long id,
    String title,
    String content,
    Long authorId,
    String createdAt,
    String updatedAt
) {
}
