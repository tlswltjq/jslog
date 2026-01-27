package com.jslog_spring.member.interfaces.rest.dto;

import com.jslog_spring.member.application.dto.SignUpResult;

public record SignUpResponse(
        Long memberId,
        String username,
        String nickname
) {
    public static SignUpResponse from(SignUpResult signUpResult) {
        return new SignUpResponse(signUpResult.memberId(), signUpResult.username(), signUpResult.nickname());
    }
}
