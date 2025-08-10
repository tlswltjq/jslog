package com.jslog_spring.domain.post.repository;

import com.jslog_spring.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    Boolean deleteByIdAndAuthorId(Long id, Long authorId);

    Page<Post> findByAuthorId(Long authorId, Pageable pageable);

    Page<Post> findByTitleContainingOrContentContaining(String keyword, String keyword2, Pageable pageable);

    @Query("select p from Post p where p.authorId = :authorId and (p.title like %:keyword% or p.content like %:keyword%)")
    Page<Post> findByAuthorIdAndKeyword(@Param("authorId") Long authorId, @Param("keyword") String keyword, Pageable pageable);
}
