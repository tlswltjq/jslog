package com.jslog_spring.domain.todo.entity;

import com.jslog_spring.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String title;
    private String description;
    private boolean completed;

    @Builder(access = AccessLevel.PRIVATE)
    public Todo(Long id, String category, String title, String description, boolean completed) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public static Todo create(String category, String title, String description) {
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be empty.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        return Todo.builder()
                .category(category)
                .title(title)
                .description(description)
                .completed(false)
                .build();
    }

    public void update(String category, String title, String description, Boolean completed) {
        if (category != null && !category.isBlank()) {
            this.category = category;
        }
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
        if (completed != null) {
            this.completed = completed;
        }
    }
}
