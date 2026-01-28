package com.jslog_spring.auth.domain.policy;

import com.jslog_spring.auth.domain.model.Account;

public interface AccountPolicy<T extends Account> {

    void validate(T account);
}
