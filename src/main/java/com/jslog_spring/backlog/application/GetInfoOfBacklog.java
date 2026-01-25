package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.application.dto.BacklogInfo;
import com.jslog_spring.backlog.domain.repository.BacklogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetInfoOfBacklog {
    private final BacklogRepository repository;

    public BacklogInfo invoke(Long requestUserId, Long backlogId) {
        return repository.findInfoByIdAndOwner(backlogId, requestUserId);
    }
}
