package com.jslog_spring.auth.domain.policy;

import com.jslog_spring.auth.domain.model.Account;

public interface AccountCreationPolicy {
    boolean supports(Account account);

    void validate(Account account);
}
