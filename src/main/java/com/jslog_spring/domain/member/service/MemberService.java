package com.jslog_spring.domain.member.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.repository.MemberRepository;
import com.jslog_spring.global.exception.ServiceException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member join(String username, String password, String nickname) {
        memberRepository.findByUsername(username)
                .ifPresent(member -> {
                    throw new ServiceException("409-1", "이미 존재하는 아이디입니다.");
                });
        Member member = Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();
        return memberRepository.save(member);
    }
}
