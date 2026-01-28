package com.jslog_spring.backlog.infrastructure;

import com.jslog_spring.backlog.application.dto.BacklogInfo;
import com.jslog_spring.backlog.domain.model.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BacklogJpaRepository extends JpaRepository<Backlog, Long> {
    @Query("""
            SELECT new com.jslog_spring.backlog.application.dto.BacklogInfo(
                b.id,
                b.ownedBy,
                b.name,
                b.desc,
                b.dueDate,
                b.isDone,
                b.createdAt,
                b.updatedAt
            )
            FROM Backlog b
            WHERE b.id = :id AND b.ownedBy = :owner
            """)
    Optional<BacklogInfo> findInfoByIdAndOwner(@Param("id") Long id, @Param("owner") Long owner);

    @Query("""
            SELECT new com.jslog_spring.backlog.application.dto.BacklogInfo(
                b.id,
                b.ownedBy,
                b.name,
                b.desc,
                b.dueDate,
                b.isDone,
                b.createdAt,
                b.updatedAt
            )
            FROM Backlog b
            WHERE b.ownedBy = :ownedBy
            """)
    List<BacklogInfo> findAllInfoByOwnedBy(@Param("ownedBy") Long ownedBy);
}
