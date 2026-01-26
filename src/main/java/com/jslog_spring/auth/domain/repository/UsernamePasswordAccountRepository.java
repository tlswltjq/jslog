package com.jslog_spring.auth.domain.repository;

import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsernamePasswordAccountRepository extends JpaRepository<UsernamePasswordAccount, Long> {
    Optional<UsernamePasswordAccount> findByEmail(String email);

    boolean existsByEmail(String email);
}
