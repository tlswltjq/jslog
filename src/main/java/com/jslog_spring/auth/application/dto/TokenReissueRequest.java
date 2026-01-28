package com.jslog_spring.auth.application.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenReissueRequest(
        @NotBlank String refreshToken
) {
}
