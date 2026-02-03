package com.jslog_spring.auth.infrastructure;

import com.jslog_spring.auth.domain.model.Account;
import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import com.jslog_spring.auth.domain.repository.AccountCommandRepository;
import com.jslog_spring.auth.domain.repository.AccountQueryRepository;
import com.jslog_spring.auth.domain.repository.UsernamePasswordAccountCommandRepository;
import com.jslog_spring.auth.domain.repository.UsernamePasswordAccountQueryRepository;
import com.jslog_spring.auth.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements
        AccountCommandRepository,
        AccountQueryRepository,
        UsernamePasswordAccountCommandRepository,
        UsernamePasswordAccountQueryRepository {
    private final AccountJpaRepository accountJpaRepository;
    private final UsernamePasswordAccountJpaRepository usernamePasswordAccountJpaRepository;

    @Override
    public Account save(Account account) {
        return accountJpaRepository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return accountJpaRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public UsernamePasswordAccount save(UsernamePasswordAccount account) {
        return usernamePasswordAccountJpaRepository.save(account);
    }

    @Override
    public UsernamePasswordAccount findByUsername(String username) {
        return usernamePasswordAccountJpaRepository.findByUsername(username)
                .orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public boolean existsByUsername(String username) {
        return usernamePasswordAccountJpaRepository.existsByUsername(username);
    }
}
