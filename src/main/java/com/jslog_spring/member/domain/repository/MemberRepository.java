package com.jslog_spring.member.domain.repository;

import com.jslog_spring.member.application.dto.MemberInfo;
import com.jslog_spring.member.domain.model.Member;

import java.util.List;

public interface MemberRepository {
    Member save(Member member);

    Member findById(Long id);

    MemberInfo findMemberInfoById(Long id);

    List<MemberInfo> findAllMemberInfos();

    boolean existsByNickname(String nickname);

    void delete(Long id);
}
