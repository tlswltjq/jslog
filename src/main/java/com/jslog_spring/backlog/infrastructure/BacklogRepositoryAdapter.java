package com.jslog_spring.backlog.infrastructure;

import com.jslog_spring.backlog.application.dto.BacklogInfo;
import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogRepository;
import com.jslog_spring.backlog.exception.BacklogNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public BacklogInfo findInfoByIdAndOwner(Long id, Long owner) {
        return repository.findInfoByIdAndOwner(id, owner)
                .orElseThrow(BacklogNotFoundException::new);
    }

    @Override
    public List<BacklogInfo> findAllInfoByOwnedBy(Long ownedBy) {
        return repository.findAllInfoByOwnedBy(ownedBy);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
