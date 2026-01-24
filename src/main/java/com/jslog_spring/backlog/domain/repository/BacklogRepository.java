package com.jslog_spring.backlog.domain.repository;

import com.jslog_spring.backlog.domain.model.Backlog;

public interface BacklogRepository {
    Backlog save(Backlog backLog);

    Backlog findById(Long id);

    Backlog done(Long id);

    Backlog unDone(Long id);

    Backlog updateName(Long id, String name);

    Backlog updateDesc(Long id, String description);

    void delete(Long id);
}
