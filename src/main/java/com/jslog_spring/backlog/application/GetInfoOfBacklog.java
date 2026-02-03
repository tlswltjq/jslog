package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.application.dto.BacklogInfo;
import com.jslog_spring.backlog.domain.repository.BacklogQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetInfoOfBacklog {
    private final BacklogQueryRepository repository;

    public BacklogInfo invoke(Long requestUserId, Long backlogId) {
        return repository.findInfoByIdAndOwner(backlogId, requestUserId);
    }
}
