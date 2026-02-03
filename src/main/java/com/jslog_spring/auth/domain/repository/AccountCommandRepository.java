package com.jslog_spring.auth.domain.repository;

import com.jslog_spring.auth.domain.model.Account;

public interface AccountCommandRepository {
    Account save(Account account);
}
