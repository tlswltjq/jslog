package com.jslog_spring.auth.application.service;

import com.jslog_spring.auth.application.dto.LoginRequest;
import com.jslog_spring.auth.application.dto.TokenReissueRequest;
import com.jslog_spring.auth.application.dto.TokenResponse;
import com.jslog_spring.auth.domain.model.RefreshToken;
import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import com.jslog_spring.auth.domain.repository.RefreshTokenCommandRepository;
import com.jslog_spring.auth.domain.repository.RefreshTokenQueryRepository;
import com.jslog_spring.auth.domain.repository.UsernamePasswordAccountQueryRepository;
import com.jslog_spring.auth.infrastructure.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import com.jslog_spring.auth.domain.service.AccountManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsernamePasswordAccountQueryRepository usernamePasswordAccountQueryRepository;
    private final AccountManager accountManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenCommandRepository refreshTokenCommandRepository;
    private final RefreshTokenQueryRepository refreshTokenQueryRepository;

    @Transactional
    public TokenResponse login(LoginRequest request) {
        UsernamePasswordAccount account = usernamePasswordAccountQueryRepository.findByUsername(request.email());

        if (!accountManager.checkPassword(account, request.password())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String role = "USER";
        Long accountId = account.getId();

        String accessToken = jwtTokenProvider.createToken(accountId, role);
        String refreshToken = jwtTokenProvider.createRefreshToken(accountId);

        Date expiryDate = jwtTokenProvider.getClaims(refreshToken).getExpiration();

        RefreshToken refreshTokenEntity = new RefreshToken(
                refreshToken, accountId, expiryDate, role);
        refreshTokenCommandRepository.save(refreshTokenEntity);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional
    public void logout(TokenReissueRequest request) {
        refreshTokenCommandRepository.deleteByToken(request.refreshToken());
    }

    @Transactional
    public TokenResponse reissue(TokenReissueRequest request) {
        if (!jwtTokenProvider.validateToken(request.refreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String requestRefreshToken = request.refreshToken();
        RefreshToken foundToken = refreshTokenQueryRepository.findByToken(requestRefreshToken);

        Long accountId = foundToken.getAccountId();
        String role = foundToken.getRole();

        String newAccessToken = jwtTokenProvider.createToken(accountId, role);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(accountId);

        java.util.Date expiryDate = jwtTokenProvider.getClaims(newRefreshToken).getExpiration();

        foundToken.updateToken(newRefreshToken, expiryDate);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
