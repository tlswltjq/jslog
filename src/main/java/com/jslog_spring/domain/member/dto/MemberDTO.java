package com.jslog_spring.domain.member.dto;

import com.jslog_spring.domain.member.entity.Member;

import java.time.LocalDateTime;

public record MemberDTO(int id, String username, String nickname, LocalDateTime createdDate, LocalDateTime modifiedDate) {
    public MemberDTO(Member member) {
        this(member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getCreateDate(),
                member.getModifyDate());
    }
}
