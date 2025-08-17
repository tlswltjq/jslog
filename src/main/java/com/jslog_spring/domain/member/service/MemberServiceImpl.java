package com.jslog_spring.domain.member.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.repository.MemberRepository;
import com.jslog_spring.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public Member join(String username, String password, String name) {
        String encodedPassword = passwordEncoder.encode(password);
        Member member = Member.create(username, encodedPassword, name);
        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }
        return memberRepository.save(member);
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

    public String signIn(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        return jwtUtil.generateAccessToken(Map.of("username", authentication.getName()));
    }
}
