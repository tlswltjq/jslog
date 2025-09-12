package com.jslog_spring.domain.member.entity;


import com.jslog_spring.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAttr extends BaseEntity {
    @Id
    private String username;

    private LocalDateTime lastLoginAt;

    @Builder(access = AccessLevel.PRIVATE)
    private MemberAttr(String username, LocalDateTime lastLoginAt) {
        this.username = username;
        this.lastLoginAt = lastLoginAt;
    }

    public static MemberAttr create(String username) {
        return MemberAttr.builder()
                .username(username)
                .lastLoginAt(LocalDateTime.now())
                .build();
    }

    public LocalDateTime updateLoginTime() {
        this.lastLoginAt = LocalDateTime.now();
        return this.lastLoginAt;
    }

    public void signOut() {
        updateLoginTime();
    }
}
