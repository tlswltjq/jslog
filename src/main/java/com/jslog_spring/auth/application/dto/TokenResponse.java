package com.jslog_spring.auth.application.dto;

public record TokenResponse(
        String accessToken, String refreshToken
) {
}
