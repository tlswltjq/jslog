package com.jslog_spring.domain.todo.entity;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
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
    @ManyToOne
    private Member member;
    private String category;
    private String title;
    private String description;
    private boolean done;

    @Builder(access = AccessLevel.PRIVATE)
    public Todo(Long id, Member member, String category, String title, String description, boolean done) {
        this.id = id;
        this.member = member;
        this.category = category;
        this.title = title;
        this.description = description;
        this.done = done;
    }

    public static Todo create(Member member, String category, String title, String description) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be empty.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (description == null) {
            return Todo.builder()
                    .member(member)
                    .category(category)
                    .title(title)
                    .description("")
                    .done(false)
                    .build();
        }

        return Todo.builder()
                .member(member)
                .category(category)
                .title(title)
                .description(description)
                .done(false)
                .build();
    }

    public void update(String category, String title, String description) {
        if (category != null && !category.isBlank()) {
            this.category = category;
        }
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
    }

    public void done() {
        this.done = true;
    }

    public void undone() {
        this.done = false;
    }

    public void toggle() {
        if (this.done) {
            this.undone();
        } else {
            this.done();
        }
    }
}
