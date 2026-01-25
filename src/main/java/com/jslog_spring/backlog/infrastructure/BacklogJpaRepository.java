package com.jslog_spring.backlog.infrastructure;

import com.jslog_spring.backlog.domain.model.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogJpaRepository extends JpaRepository<Backlog, Long> {
}
