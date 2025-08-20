package com.jslog_spring.domain.post.dto;

import com.jslog_spring.domain.post.entity.Post;

import java.time.format.DateTimeFormatter;

public record AllPostResponseDto(
        Long id,
        String title,
        Long authorId,
        String createdAt,
        String updatedAt
) {
    public static AllPostResponseDto fromEntity(Post post) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new AllPostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getMember().getId(),
                post.getCreateDate().format(formatter),
                post.getModifyDate().format(formatter)
        );
    }
}
