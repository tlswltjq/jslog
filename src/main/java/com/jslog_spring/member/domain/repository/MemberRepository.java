package com.jslog_spring.member.domain.repository;

import com.jslog_spring.member.domain.model.Member;

public interface MemberRepository {
    Member save(Member member);

    Member findById(Long id);

    boolean existsByNickname(String nickname);

    void delete(Long id);
}
