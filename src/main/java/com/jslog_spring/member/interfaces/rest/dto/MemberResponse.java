package com.jslog_spring.member.interfaces.rest.dto;

import com.jslog_spring.member.application.dto.MemberInfo;
import com.jslog_spring.member.domain.model.MemberType;

import java.time.LocalDateTime;

public record MemberResponse(
        String nickname,
        MemberType type,
        String bio,
        LocalDateTime createdAt
) {
    public static MemberResponse from(MemberInfo info) {
        return new MemberResponse(
                info.nickname(),
                info.type(),
                info.bio(),
                info.createdAt()
        );
    }
}
