package com.jslog_spring.auth.domain.repository;

import com.jslog_spring.auth.domain.model.Account;

public interface AccountQueryRepository {
    Account findById(Long id);
}
