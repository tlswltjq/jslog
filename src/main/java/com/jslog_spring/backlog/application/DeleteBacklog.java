package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogCommandRepository;
import com.jslog_spring.backlog.domain.repository.BacklogQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteBacklog {
    private final BacklogCommandRepository commandRepository;
    private final BacklogQueryRepository queryRepository;

    public Long invoke(Long requestUserId, Long backlogId) {
        Backlog backlog = queryRepository.findById(backlogId);

        backlog.checkOwner(requestUserId);

        commandRepository.delete(backlogId);
        return backlogId;
    }
}
