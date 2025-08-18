package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.post.entity.Post;
import org.springframework.data.domain.Page;

public interface PostService {
    Post createPost(Long authorId, String title, String content);

    Post updatePost(Long authorId, Long postId, String title, String content);

    void deletePost(Long authorId, Long postId);

    Post getPost(Long postId);

    Page<Post> getAllPosts(int page, int size);

    Page<Post> getPostsByAuthor(Long authorId, int page, int size);

    Page<Post> searchPosts(String keyword, int page, int size);

    Page<Post> searchPostsByAuthor(Long authorId, String keyword, int page, int size);

}