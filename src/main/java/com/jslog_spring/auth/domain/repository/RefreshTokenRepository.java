package com.jslog_spring.auth.domain.repository;

import com.jslog_spring.auth.domain.model.RefreshToken;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);

    RefreshToken findByToken(String token);

    RefreshToken findByAccountId(Long accountId);

    void deleteByToken(String token);
}
