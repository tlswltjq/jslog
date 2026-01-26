package com.jslog_spring.member.domain.policy;

import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.repository.MemberRepository;
import com.jslog_spring.member.exception.NicknameDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NicknameDuplicationPolicy implements MemberPolicy {
    private final MemberRepository memberRepository;

    @Override
    public void validate(Member member) {
        if (memberRepository.existsByNickname(member.getNickname())) {
            throw new NicknameDuplicationException();
        }
    }
}
