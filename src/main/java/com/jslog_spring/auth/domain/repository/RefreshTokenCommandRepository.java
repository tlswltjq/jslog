package com.jslog_spring.auth.domain.repository;

import com.jslog_spring.auth.domain.model.RefreshToken;

public interface RefreshTokenCommandRepository {
    RefreshToken save(RefreshToken refreshToken);

    void deleteByToken(String token);
}
