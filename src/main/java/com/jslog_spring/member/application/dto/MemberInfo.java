package com.jslog_spring.member.application.dto;

import com.jslog_spring.member.domain.model.MemberType;

import java.time.LocalDateTime;

public record MemberInfo(
        String nickname,
        MemberType type,
        String bio,
        LocalDateTime createdAt
) {
}
