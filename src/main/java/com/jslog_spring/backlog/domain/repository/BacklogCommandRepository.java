package com.jslog_spring.backlog.domain.repository;

import com.jslog_spring.backlog.domain.model.Backlog;

public interface BacklogCommandRepository {
    Backlog save(Backlog backLog);

    void delete(Long id);
}
