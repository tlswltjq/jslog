package com.jslog_spring.auth.domain.repository;

import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;

public interface UsernamePasswordAccountCommandRepository {
    UsernamePasswordAccount save(UsernamePasswordAccount account);
}
