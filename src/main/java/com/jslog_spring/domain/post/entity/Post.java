package com.jslog_spring.domain.post.entity;

import com.jslog_spring.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, unique = true, length = 255)
    private String slug;

    @Lob // MySQL에서는 LONGTEXT에 해당
    @Column(name = "content_markdown", nullable = false)
    private String contentMarkdown;

    @Column(length = 500)
    private String description;

    @Column(name = "views_count", nullable = false)
    private int viewsCount = 0;

    @Column(name = "is_published", nullable = false)
    private boolean isPublished = false;

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateSlug(String slug) {
        this.slug = slug;
    }

    public void updateContentMarkdown(String contentMarkdown) {
        this.contentMarkdown = contentMarkdown;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void increaseViewsCount() {
        this.viewsCount++;
    }

    public void togglePublishedStatus() {
        this.isPublished = !this.isPublished;
    }
}