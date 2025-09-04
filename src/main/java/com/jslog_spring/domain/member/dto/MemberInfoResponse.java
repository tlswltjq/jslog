package com.jslog_spring.domain.member.dto;

import com.jslog_spring.domain.member.entity.Member;

public record MemberInfoResponse(
        String name
) {
    public MemberInfoResponse (Member member){
        this(
                member.getName()
        );
    }
}
