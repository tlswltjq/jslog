package com.jslog_spring.member.domain.policy;

import com.jslog_spring.member.domain.model.Member;

public interface SignUpPolicy {
    void validate(Member member);
}
