package com.jslog_spring.domain.todo.repotisory;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByMember(Member member);

    List<Todo> findByMemberAndCategory(Member member, String category);
}
