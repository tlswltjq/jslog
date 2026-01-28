package com.jslog_spring.backlog.domain.model;

import com.jslog_spring.backlog.exception.BacklogOwnerMismatchException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ownedBy;
    private String name;
    private String desc;
    private LocalDateTime dueDate;
    private Boolean isDone;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Backlog(Long id, Long ownedBy, String name, String desc, LocalDateTime dueDate, Boolean isDone) {
        this.id = id;
        this.ownedBy = ownedBy;
        this.name = name;
        this.desc = desc;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    public static Backlog of(Long userId, String name, String desc, LocalDateTime dueDate) {
        return Backlog.builder()
                .ownedBy(userId)
                .name(name)
                .desc(desc)
                .dueDate(dueDate)
                .isDone(false)
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

    public void update(String name, String desc, LocalDateTime dueDate) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (desc != null) {
            this.desc = desc;
        }
        if (dueDate != null) {
            this.dueDate = dueDate;
        }
    }
}
