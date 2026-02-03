package com.jslog_spring.backlog.domain.repository;

import com.jslog_spring.backlog.application.dto.BacklogInfo;
import com.jslog_spring.backlog.domain.model.Backlog;

import java.util.List;

public interface BacklogQueryRepository {
    Backlog findById(Long id);

    BacklogInfo findInfoByIdAndOwner(Long id, Long owner);

    List<BacklogInfo> findAllInfoByOwnedBy(Long ownedBy);
}
