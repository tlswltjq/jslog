package com.jslog_spring.member.domain.policy;

import com.jslog_spring.member.domain.model.Member;

public interface MemberPolicy {
    void validate(Member member);
}
