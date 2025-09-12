package com.jslog_spring.domain.member.repository;

import com.jslog_spring.domain.member.entity.MemberAttr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAttrRepository extends JpaRepository<MemberAttr, String> {
}
