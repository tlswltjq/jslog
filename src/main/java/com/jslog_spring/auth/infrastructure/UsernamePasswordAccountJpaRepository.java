package com.jslog_spring.auth.infrastructure;

import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsernamePasswordAccountJpaRepository extends JpaRepository<UsernamePasswordAccount, Long> {
    Optional<UsernamePasswordAccount> findByUsername(String username);

    boolean existsByUsername(String username);
}
