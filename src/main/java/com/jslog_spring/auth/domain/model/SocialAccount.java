package com.jslog_spring.auth.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@PrimaryKeyJoinColumn(name = "account_id")
public class SocialAccount extends Account {

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Builder
    private SocialAccount(Long memberId, String providerId, AuthProvider authProvider) {
        super(memberId, authProvider);
        this.providerId = providerId;
    }
}
