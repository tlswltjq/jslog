package com.jslog_spring.auth.domain.policy;

import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import com.jslog_spring.auth.domain.repository.UsernamePasswordAccountRepository;
import com.jslog_spring.auth.exception.UsernameDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameDuplicationPolicy implements AccountPolicy<UsernamePasswordAccount> {
    private final UsernamePasswordAccountRepository accountRepository;

    @Override
    public void validate(UsernamePasswordAccount account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new UsernameDuplicationException();
        }
    }
}
