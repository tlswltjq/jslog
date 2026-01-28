package com.jslog_spring.auth.infrastructure;

import com.jslog_spring.auth.domain.model.RefreshToken;
import com.jslog_spring.auth.domain.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.jslog_spring.auth.exception.RefreshTokenNotFoundException;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {
    private final RefreshTokenJpaRepository repository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return repository.save(refreshToken);
    }

    @Override
    public RefreshToken findByToken(String token) {
        return repository.findByToken(token)
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Override
    public RefreshToken findByAccountId(Long accountId) {
        return repository.findByAccountId(accountId)
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Override
    public void deleteByToken(String token) {
        repository.deleteByToken(token);
    }
}
