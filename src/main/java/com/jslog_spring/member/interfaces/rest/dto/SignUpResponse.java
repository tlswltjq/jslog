package com.jslog_spring.member.interfaces.rest.dto;

public record SignUpResponse(
        Long memberId,
        String nickname
) {
    public static SignUpResponse of(Long memberId, String nickname) {
        return new SignUpResponse(memberId, nickname);
    }
}
