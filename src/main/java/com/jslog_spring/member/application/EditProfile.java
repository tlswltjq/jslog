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
public class EditProfile {
    private final List<MemberPolicy> memberPolicies;
    private final MemberRepository repository;

    //프로필 수정(현재는 bio만, Member의 변동으로 프로필에 담을 내용이 많아지면 파라미터 및 구현 수정 필요)
    public void invoke(Long memberId, String bio) {
        Member member = repository.findById(memberId);
        member.changeBio(bio);

        memberPolicies.forEach(policy -> policy.validate(member));
    }
}
