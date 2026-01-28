package com.jslog_spring.member.interfaces.rest.dto;

public record WithdrawResponse(
        Long memberId
) {
    public static WithdrawResponse from(Long memberId) {
        return new WithdrawResponse(memberId);
    }
}
