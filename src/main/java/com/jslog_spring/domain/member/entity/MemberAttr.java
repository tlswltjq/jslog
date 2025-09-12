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
    @Column(nullable = true)
    private String accessToken;

    private LocalDateTime lastLoginAt;

    @Builder(access = AccessLevel.PRIVATE)
    private MemberAttr(String username, String accessToken, LocalDateTime lastLoginAt) {
        this.username = username;
        this.accessToken = accessToken;
        this.lastLoginAt = lastLoginAt;
    }

    public static MemberAttr create(String username, String accessToken) {
        return MemberAttr.builder()
                .username(username)
                .accessToken(accessToken)
                .lastLoginAt(LocalDateTime.now())
                .build();
    }

    public void updateAccessToken(String token) {
        this.accessToken = token;
        this.lastLoginAt = LocalDateTime.now();
    }

    public void signOut() {
        this.accessToken = null;
    }
}
