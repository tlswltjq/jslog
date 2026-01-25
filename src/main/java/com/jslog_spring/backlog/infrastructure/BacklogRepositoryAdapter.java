package com.jslog_spring.backlog.infrastructure;

import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BacklogRepositoryAdapter implements BacklogRepository {
    private final BacklogRepository repository;

    @Override
    public Backlog save(Backlog backLog) {
        return repository.save(backLog);
    }

    @Override
    public Backlog findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
