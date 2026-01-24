package com.jslog_spring.backlog.domain.model;

import com.jslog_spring.backlog.exception.BacklogOwnerMismatchException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Backlog {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ownedBy;
    private String name;
    private String desc;
    private LocalDateTime dueDate;
    private Boolean isDone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private Backlog(Long id, Long ownedBy, String name, String desc, LocalDateTime dueDate, Boolean isDone,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ownedBy = ownedBy;
        this.name = name;
        this.desc = desc;
        this.dueDate = dueDate;
        this.isDone = isDone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Backlog of(Long userId, String name, String desc, LocalDateTime dueDate) {
        return Backlog.builder()
                .ownedBy(userId)
                .name(name)
                .desc(desc)
                .dueDate(dueDate)
                .isDone(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void checkOwner(Long userId) {
        if (!this.ownedBy.equals(userId)) {
            throw new BacklogOwnerMismatchException();
        }
    }

    public void done() {
        this.isDone = true;
    }

    public void undone() {
        this.isDone = false;
    }
}
