package com.jslog_spring.auth.application.service;

import com.jslog_spring.auth.application.dto.LoginRequest;
import com.jslog_spring.auth.application.dto.TokenReissueRequest;
import com.jslog_spring.auth.application.dto.TokenResponse;
import com.jslog_spring.auth.domain.model.RefreshToken;
import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import com.jslog_spring.auth.domain.repository.RefreshTokenRepository;
import com.jslog_spring.auth.domain.repository.UsernamePasswordAccountRepository;
import com.jslog_spring.auth.domain.service.AccountManager;
import com.jslog_spring.auth.infrastructure.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsernamePasswordAccountRepository usernamePasswordAccountRepository;
    @Mock
    private AccountManager accountManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private Claims claims;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("로그인 테스트")
    void login() {
        // given
        LoginRequest request = new LoginRequest("email", "password");
        UsernamePasswordAccount account = UsernamePasswordAccount.of(1L, "email", "encoded");

        given(usernamePasswordAccountRepository.findByUsername(request.email())).willReturn(account);
        given(accountManager.checkPassword(account, request.password())).willReturn(true);
        given(jwtTokenProvider.createToken(any(), anyString())).willReturn("access");
        given(jwtTokenProvider.createRefreshToken(any())).willReturn("refresh");
        given(jwtTokenProvider.getClaims("refresh")).willReturn(claims);
        given(claims.getExpiration()).willReturn(new Date());

        // when
        TokenResponse response = authService.login(request);

        // then
        assertThat(response.accessToken()).isEqualTo("access");
        assertThat(response.refreshToken()).isEqualTo("refresh");
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    @DisplayName("토큰 재발급 테스트")
    void reissue() {
        // given
        TokenReissueRequest request = new TokenReissueRequest("old_refresh");
        RefreshToken existingTokenEntity = new RefreshToken("old_refresh", 1L, new Date(), "USER");

        given(jwtTokenProvider.validateToken("old_refresh")).willReturn(true);
        given(refreshTokenRepository.findByToken("old_refresh")).willReturn(existingTokenEntity);
        given(jwtTokenProvider.createToken(1L, "USER")).willReturn("new_access");
        given(jwtTokenProvider.createRefreshToken(1L)).willReturn("new_refresh");
        given(jwtTokenProvider.getClaims("new_refresh")).willReturn(claims);
        given(claims.getExpiration()).willReturn(new Date());

        // when
        TokenResponse response = authService.reissue(request);

        // then
        assertThat(response.accessToken()).isEqualTo("new_access");
        assertThat(response.refreshToken()).isEqualTo("new_refresh");
        assertThat(existingTokenEntity.getToken()).isEqualTo("new_refresh"); // Entity state updated
    }

    @Test
    @DisplayName("로그아웃 테스트")
    void logout() {
        // given
        TokenReissueRequest request = new TokenReissueRequest("refresh");

        // when
        authService.logout(request);

        // then
        verify(refreshTokenRepository).deleteByToken("refresh");
    }
}
