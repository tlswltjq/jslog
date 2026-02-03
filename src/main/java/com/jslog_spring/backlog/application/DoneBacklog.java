package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DoneBacklog {
    private final BacklogQueryRepository repository;

    public Long invoke(Long requestUserId, Long backlogId) {
        Backlog backlog = repository.findById(backlogId);

        backlog.checkOwner(requestUserId);
        backlog.done();

        return backlog.getId();
    }
}
