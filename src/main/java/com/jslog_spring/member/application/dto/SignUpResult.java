package com.jslog_spring.member.application.dto;

public record SignUpResult(
        Long memberId,
        String username,
        String nickname
) {
}
