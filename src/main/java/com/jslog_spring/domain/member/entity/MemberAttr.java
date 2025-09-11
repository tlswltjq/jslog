package com.jslog_spring.domain.member.entity;


import com.jslog_spring.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAttr extends BaseEntity {
    //NOTE : 레포지토리 시그니처 Long 으로 접근고려시 @MapsId 으로 변경
    @Id
    @OneToOne(fetch = FetchType.EAGER)
    private Member member;
    @Column(nullable = true)
    private String accessToken;

    private LocalDateTime lastLoginAt;

    public void updateAccessToken(String token) {
        this.accessToken = token;
        this.lastLoginAt = LocalDateTime.now();
    }

    public void logout() {
        this.accessToken = null;
    }
}
