package com.jslog_spring.member.application.dto;

public record ProfileEditCommand(
        String nickname,
        String bio
) {
}
