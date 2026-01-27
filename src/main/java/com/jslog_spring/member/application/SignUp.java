package com.jslog_spring.member.application;

import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import com.jslog_spring.auth.domain.repository.UsernamePasswordAccountRepository;
import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.model.MemberType;
import com.jslog_spring.member.domain.policy.MemberPolicy;
import com.jslog_spring.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
//TODO : 수정 필수
public class SignUp {
    private final List<MemberPolicy> memberPolicies;
    private final MemberRepository memberRepository;
    private final UsernamePasswordAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Long invoke(String nickname, String email, String password) {
        if (accountRepository.existsByUsername(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        Member member = Member.of(nickname, MemberType.USER);
        memberPolicies.forEach(policy -> policy.validate(member));
        Member savedMember = memberRepository.save(member);

        UsernamePasswordAccount account = UsernamePasswordAccount.of(
                savedMember.getId(),
                email,
                password,
                passwordEncoder);
        accountRepository.save(account);

        return savedMember.getId();
    }
}
