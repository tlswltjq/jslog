package com.jslog_spring.member.application;

import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.model.MemberType;
import com.jslog_spring.member.domain.policy.MemberPolicy;
import com.jslog_spring.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUp {
    private final List<MemberPolicy> memberPolicies;
    private final MemberRepository repository;

    public Long invoke(String nickname) {
        Member member = Member.of(nickname, MemberType.USER);
        memberPolicies.forEach(policy -> policy.validate(member));
        Member savedMember = repository.save(member);
        return savedMember.getId();
    }
}
