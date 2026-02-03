package com.jslog_spring.auth.domain.service;

import com.jslog_spring.auth.domain.model.Account;
import com.jslog_spring.auth.domain.model.AuthProvider;
import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import com.jslog_spring.auth.domain.repository.AccountCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountManager {
    private final PasswordEncoder passwordEncoder;
    private final AccountCommandRepository accountRepository;

    public UsernamePasswordAccount createUsernamePasswordAccount(Long memberId, String email, String password) {
        return UsernamePasswordAccount.builder()
                .memberId(memberId)
                .username(email)
                .password(passwordEncoder.encode(password))
                .authProvider(AuthProvider.EMAIL)
                .build();
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public void changePassword(UsernamePasswordAccount account, String newPassword) {
        account.changePassword(passwordEncoder.encode(newPassword));
    }

    public boolean checkPassword(UsernamePasswordAccount account, String rawPassword) {
        return passwordEncoder.matches(rawPassword, account.getPassword());
    }
}
