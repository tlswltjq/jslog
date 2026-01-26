package com.jslog_spring.member.application;

import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.policy.MemberPolicy;
import com.jslog_spring.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangeNickname {
    private final List<MemberPolicy> memberPolicies;
    private final MemberRepository repository;

    public Long invoke(Long memberId, String nickname) {
        Member member = repository.findById(memberId);
        member.changeNickname(nickname);

        memberPolicies.forEach(policy -> policy.validate(member));
        return memberId;
    }
}