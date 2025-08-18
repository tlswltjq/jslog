package com.jslog_spring.domain.member.entity;

import com.jslog_spring.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public static Member create(String username, String password, String name) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        return Member.builder()
                .username(username)
                .password(password)
                .name(name)
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }
}
