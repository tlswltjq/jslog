package com.jslog_spring.domain.post.entity;

import com.jslog_spring.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    private Long authorId;
    private String title;
    private String content;

    public Post(Long authorId, String title, String content) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
    }

    @Builder(access = AccessLevel.PRIVATE)
    public static Post create(Long authorId, String title, String content) {
        if (authorId <= 0) {
            throw new IllegalArgumentException("AuthorId must be positive.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be empty.");
        }

        return Post.builder()
                .authorId(authorId)
                .title(title)
                .content(content)
                .build();
    }

    public void update(String title, String content) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
    }
}
