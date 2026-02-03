package com.jslog_spring.member.domain.repository;

import com.jslog_spring.member.domain.model.Member;

public interface MemberCommandRepository {
    Member save(Member member);

    void delete(Long id);
}
