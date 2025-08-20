package com.jslog_spring.domain.member.dto;

import com.jslog_spring.domain.member.entity.Member;
import java.time.LocalDateTime;

public record JoinResponse(
        Long id,
        String username,
        String name,
        String role,
        LocalDateTime createDate
) {
    public JoinResponse(Member member) {
        this(
                member.getId(),
                member.getUsername(),
                member.getName(),
                member.getRole(),
                member.getCreateDate()
        );
    }
}
