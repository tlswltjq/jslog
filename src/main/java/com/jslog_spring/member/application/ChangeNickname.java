package com.jslog_spring.member.application;

import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.policy.NicknameChangePolicy;
import com.jslog_spring.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangeNickname {
    private final List<NicknameChangePolicy> policies;
    private final MemberRepository repository;

    public Long invoke(Long memberId, String nickname) {
        Member member = repository.findById(memberId);

        if (!member.getNickname().equals(nickname)) {
            policies.forEach(policy -> policy.validate(nickname));
        }

        member.changeNickname(nickname);
        return memberId;
    }
}