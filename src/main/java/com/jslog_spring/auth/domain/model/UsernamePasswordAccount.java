package com.jslog_spring.auth.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public static UsernamePasswordAccount of(Long memberId, String username, String password, PasswordEncoder passwordEncoder) {
        return UsernamePasswordAccount.builder()
                .memberId(memberId)
                .username(username)
                .password(passwordEncoder.encode(password))
                .authProvider(AuthProvider.EMAIL)
                .build();
    }

    public boolean passwordMatches(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.password);
    }
}
