package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class EditBacklog {
    private final BacklogQueryRepository repository;

    public Long invoke(Long requestUserId, Long backlogId, String newName, String newDesc, LocalDateTime newDueDate) {
        Backlog backlog = repository.findById(backlogId);

        backlog.checkOwner(requestUserId);

        backlog.update(newName, newDesc, newDueDate);
        return backlog.getId();
    }
}
