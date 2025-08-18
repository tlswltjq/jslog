package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(Long authorId, String title, String content);

    Post updatePost(Long postId, String title, String content);

    void deletePost(Long postId);

    Optional<Post> getPost(Long postId);

    List<Post> getAllPosts(int page, int size);

    List<Post> getPostsByAuthor(Long authorId, int page, int size);

    List<Post> searchPosts(String keyword, int page, int size);

    List<Post> searchPostsByAuthor(Long authorId, String keyword, int page, int size);

    long countPostsByAuthor(Long authorId);

    long countAllPosts();

}
