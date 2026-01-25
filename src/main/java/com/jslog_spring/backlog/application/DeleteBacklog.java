package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteBacklog {
    private final BacklogRepository repository;

    public void invoke(Long requestUserId, Long backlogId){
        Backlog backlog = repository.findById(backlogId);

        backlog.checkOwner(requestUserId);

        repository.delete(backlogId);
    }
}
