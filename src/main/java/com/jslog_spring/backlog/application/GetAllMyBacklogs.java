package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.application.dto.BacklogInfo;
import com.jslog_spring.backlog.domain.repository.BacklogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetAllMyBacklogs {
    private final BacklogRepository repository;

    public List<BacklogInfo> invoke(Long requestUserId) {
        return repository.findAllInfoByOwnedBy(requestUserId);
    }
}
