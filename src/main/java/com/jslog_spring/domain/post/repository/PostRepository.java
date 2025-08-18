package com.jslog_spring.domain.post.repository;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByMember(Member member, Pageable pageable);

    Page<Post> findByTitleContainingOrContentContaining(String keyword, String keyword2, Pageable pageable);

    @Query("select p from Post p where p.member = :member and (p.title like %:keyword% or p.content like %:keyword%)")
    Page<Post> findByMemberAndKeyword(@Param("member") Member member, @Param("keyword") String keyword, Pageable pageable);
}