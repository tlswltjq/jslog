package com.jslog_spring.auth.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Long accountId;

    private Date expiryDate;

    private String role;

    public RefreshToken(String token, Long accountId, Date expiryDate, String role) {
        this.token = token;
        this.accountId = accountId;
        this.expiryDate = expiryDate;
        this.role = role;
    }

    public void updateToken(String token, Date expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
    }
}
