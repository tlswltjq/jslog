package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(int authorId, String title, String content);

    Post updatePost(int postId, String title, String content);

    void deletePost(int postId);

    Optional<Post> getPost(int postId);

    List<Post> getAllPosts(int page, int size);

    List<Post> getPostsByAuthor(int authorId, int page, int size);

    List<Post> searchPosts(String keyword, int page, int size);

    List<Post> searchPostsByAuthor(int authorId, String keyword, int page, int size);

    long countPostsByAuthor(int authorId);

    long countAllPosts();

}
