package com.jslog_spring.auth.domain.repository;

import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;

public interface UsernamePasswordAccountRepository {
    UsernamePasswordAccount save(UsernamePasswordAccount account);

    UsernamePasswordAccount findByUsername(String username);

    boolean existsByUsername(String username);
}
