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
public class UsernamePasswordAccount extends Account {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Builder
    private UsernamePasswordAccount(Long memberId, String username, String password, AuthProvider authProvider) {
        super(memberId, authProvider);
        this.username = username;
        this.password = password;
    }

    public static UsernamePasswordAccount of(Long memberId, String username, String encryptedPassword) {
        return UsernamePasswordAccount.builder()
                .memberId(memberId)
                .username(username)
                .password(encryptedPassword)
                .authProvider(AuthProvider.EMAIL)
                .build();
    }

    public void changePassword(String encryptedNewPassword) {
        this.password = encryptedNewPassword;
    }
}
