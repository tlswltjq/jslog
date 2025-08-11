package com.jslog_spring.domain.post.dto;

import com.jslog_spring.domain.post.entity.Post;

import java.time.format.DateTimeFormatter;

public record PostResponseDto(
    Long id,
    String title,
    String content,
    Long authorId,
    String createdAt,
    String updatedAt
) {
    public static PostResponseDto fromEntity(Post post) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new PostResponseDto(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getAuthorId(),
            post.getCreateDate().format(formatter),
            post.getModifyDate().format(formatter)
        );
    }
}
