package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.post.entity.Post;
import org.springframework.data.domain.Page;

public interface PostService {
    Post createPost(Member author, String title, String content);

    Post updatePost(Member author, Long postId, String title, String content);

    void deletePost(Member author, Long postId);

    Post getPost(Long postId);

    Page<Post> getAllPosts(int page, int size);

    Page<Post> getPostsByAuthor(Member author, int page, int size);

    Page<Post> searchPosts(String keyword, int page, int size);

    Page<Post> searchPostsByAuthor(Member author, String keyword, int page, int size);

}