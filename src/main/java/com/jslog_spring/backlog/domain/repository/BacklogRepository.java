package com.jslog_spring.backlog.domain.repository;

import com.jslog_spring.backlog.application.dto.BacklogInfo;
import com.jslog_spring.backlog.domain.model.Backlog;

import java.util.List;

public interface BacklogRepository {
    Backlog save(Backlog backLog);

    Backlog findById(Long id);

    BacklogInfo findInfoByIdAndOwner(Long id, Long owner);

    List<BacklogInfo> findAllInfoByOwnedBy(Long ownedBy);

    void delete(Long id);
}
