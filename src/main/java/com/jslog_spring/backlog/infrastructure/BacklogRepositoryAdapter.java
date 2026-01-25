package com.jslog_spring.backlog.infrastructure;

import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogRepository;
import com.jslog_spring.backlog.exception.BacklogNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BacklogRepositoryAdapter implements BacklogRepository {
    private final BacklogJpaRepository repository;

    @Override
    public Backlog save(Backlog backLog) {
        return repository.save(backLog);
    }

    @Override
    public Backlog findById(Long id) {
        return repository.findById(id)
                .orElseThrow(BacklogNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
