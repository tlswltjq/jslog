package com.jslog_spring.backlog.domain.repository;

import com.jslog_spring.backlog.domain.model.Backlog;

public interface BacklogRepository {
    Backlog save(Backlog backLog);

    Backlog findById(Long id);

    void delete(Long id);
}
