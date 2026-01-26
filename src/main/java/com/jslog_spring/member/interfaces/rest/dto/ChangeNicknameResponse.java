package com.jslog_spring.member.interfaces.rest.dto;

public record ChangeNicknameResponse(
        Long memberId,
        String nickname
) {
    public static ChangeNicknameResponse of(Long memberId, String nickname) {
        return new ChangeNicknameResponse(memberId, nickname);
    }
}
