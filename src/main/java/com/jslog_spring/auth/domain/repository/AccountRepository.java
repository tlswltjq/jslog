package com.jslog_spring.auth.domain.repository;

import com.jslog_spring.auth.domain.model.Account;

public interface AccountRepository {
    Account save(Account account);

    Account findById(Long id);
}
