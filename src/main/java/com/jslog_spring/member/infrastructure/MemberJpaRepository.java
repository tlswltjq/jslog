package com.jslog_spring.member.infrastructure;

import com.jslog_spring.member.application.dto.MemberInfo;
import com.jslog_spring.member.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsByNickname(String nickname);

    @Query("""
            SELECT new com.jslog_spring.member.application.dto.MemberInfo(
                m.nickname,
                m.type,
                m.bio,
                m.createdAt
            )
            FROM Member m
            WHERE m.id = :id
            """)
    Optional<MemberInfo> findMemberInfoById(@Param("id") Long id);

    @Query("""
            SELECT new com.jslog_spring.member.application.dto.MemberInfo(
                m.nickname,
                m.type,
                m.bio,
                m.createdAt
            )
            FROM Member m
            """)
    List<MemberInfo> findAllMemberInfos();
}
