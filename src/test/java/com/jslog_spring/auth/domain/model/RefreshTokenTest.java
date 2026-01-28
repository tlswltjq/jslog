package com.jslog_spring.auth.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenTest {

    @Test
    @DisplayName("리프레시 토큰 생성 및 업데이트 테스트")
    void createAndUpdateToken() {
        Date expiryDate = new Date();
        RefreshToken refreshToken = new RefreshToken("token", 1L, expiryDate, "USER");

        assertThat(refreshToken.getToken()).isEqualTo("token");
        assertThat(refreshToken.getAccountId()).isEqualTo(1L);
        assertThat(refreshToken.getRole()).isEqualTo("USER");

        String newToken = "newToken";
        Date newExpiryDate = new Date();
        refreshToken.updateToken(newToken, newExpiryDate);

        assertThat(refreshToken.getToken()).isEqualTo(newToken);
        assertThat(refreshToken.getExpiryDate()).isEqualTo(newExpiryDate);
    }
}
