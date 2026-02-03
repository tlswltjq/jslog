package com.jslog_spring.member.application;

import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import com.jslog_spring.auth.domain.policy.AccountCreationPolicy;
import com.jslog_spring.auth.domain.service.AccountManager;
import com.jslog_spring.member.application.dto.SignUpResult;
import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.model.MemberType;
import com.jslog_spring.member.domain.policy.SignUpPolicy;
import com.jslog_spring.member.domain.repository.MemberCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUp {
    private final List<SignUpPolicy> memberPolicies;
    private final List<AccountCreationPolicy> accountPolicies;
    private final MemberCommandRepository memberRepository;
    private final AccountManager accountManager;

    public SignUpResult invoke(String nickname, String email, String password) {
        Member member = Member.of(nickname, MemberType.USER);
        memberPolicies.forEach(policy -> policy.validate(member));
        Member savedMember = memberRepository.save(member);

        UsernamePasswordAccount account = accountManager.createUsernamePasswordAccount(
                savedMember.getId(),
                email,
                password);
        accountPolicies.stream()
                .filter(policy -> policy.supports(account))
                .forEach(policy -> policy.validate(account));
        accountManager.saveAccount(account);

        return new SignUpResult(member.getId(), account.getUsername(), member.getNickname());
    }
}
