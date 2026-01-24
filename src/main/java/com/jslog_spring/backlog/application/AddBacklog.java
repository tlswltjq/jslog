package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AddBacklog {
    private final BacklogRepository repository;

    public Long invoke(Long requestUserId, String name, String desc, LocalDateTime dueDate) {
        Backlog saved = repository.save(Backlog.of(requestUserId, name, desc, dueDate));
        return saved.getId();
    }
}
