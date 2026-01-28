package com.jslog_spring.member.application;

import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import com.jslog_spring.auth.domain.repository.UsernamePasswordAccountRepository;
import com.jslog_spring.auth.domain.service.AccountManager;
import com.jslog_spring.member.application.dto.SignUpResult;
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
    private final MemberRepository memberRepository;
    private final UsernamePasswordAccountRepository accountRepository;
    private final AccountManager accountManager;

    public SignUpResult invoke(String nickname, String email, String password) {
        if (accountRepository.existsByUsername(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        Member member = Member.of(nickname, MemberType.USER);
        memberPolicies.forEach(policy -> policy.validate(member));
        Member savedMember = memberRepository.save(member);

        UsernamePasswordAccount account = accountManager.createUsernamePasswordAccount(
                savedMember.getId(),
                email,
                password);
        accountRepository.save(account);

        return new SignUpResult(member.getId(), account.getUsername(), member.getNickname());
    }
}
