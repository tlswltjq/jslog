package com.jslog_spring.auth.domain.policy;

import com.jslog_spring.auth.domain.model.Account;
import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import com.jslog_spring.auth.domain.repository.UsernamePasswordAccountQueryRepository;
import com.jslog_spring.auth.exception.UsernameDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameRule implements AccountCreationPolicy {
    private final UsernamePasswordAccountQueryRepository accountRepository;

    @Override
    public boolean supports(Account account) {
        return account instanceof UsernamePasswordAccount;
    }

    @Override
    public void validate(Account account) {
        UsernamePasswordAccount upAccount = (UsernamePasswordAccount) account;
        if (accountRepository.existsByUsername(upAccount.getUsername())) {
            throw new UsernameDuplicationException();
        }
    }
}
