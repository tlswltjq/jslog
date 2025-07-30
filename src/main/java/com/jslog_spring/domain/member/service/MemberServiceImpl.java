package com.jslog_spring.domain.member.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public void join(String username, String password, String name) {
        Member member = Member.create(username, password, name);
        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }
        memberRepository.save(member);
    }

    public Member getMember(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member.orElseThrow(() -> new IllegalArgumentException("Member not found."));
    }

    public Member updateUserName(Member member, String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("New name cannot be empty.");
        }
        member.updateName(newName);
        return memberRepository.save(member);
    }
}
