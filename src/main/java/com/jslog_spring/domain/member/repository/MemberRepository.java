package com.jslog_spring.domain.member.repository;

import com.jslog_spring.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
