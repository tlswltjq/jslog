package com.jslog_spring.domain.member.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.exception.InvalidInputValueException;
import com.jslog_spring.domain.member.exception.MemberNotFoundException;
import com.jslog_spring.domain.member.exception.UserNameDuplicationException;
import com.jslog_spring.domain.member.repository.MemberRepository;
import com.jslog_spring.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisTemplate redisTemplate;

    public Member join(String username, String password, String name) {
        String encodedPassword = passwordEncoder.encode(password);
        Member member = Member.create(username, encodedPassword, name);
        if (memberRepository.existsByUsername(username)) {
            throw new UserNameDuplicationException();
        }
        return memberRepository.save(member);
    }

    public Member getMember(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member.orElseThrow(MemberNotFoundException::new);
    }

    public Member updateUserName(Member member, String newName) {
        if (newName == null || newName.isBlank()) {
            throw new InvalidInputValueException();
        }
        member.updateName(newName);
        return memberRepository.save(member);
    }

    public Pair<String, String> signIn(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String accessToken = jwtUtil.generateAccessToken(Map.of("username", authentication.getName()));
        String refreshToken = jwtUtil.generateRefreshToken(Map.of("username", authentication.getName()));

        redisTemplate.opsForValue().set(
                authentication.getName(),
                refreshToken,
                365,
                TimeUnit.DAYS
        );

        return Pair.of(accessToken, refreshToken);
    }
}
