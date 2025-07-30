package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public Post createPost(int authorId, String title, String content) {
        Post newPost = Post.create(authorId, title, content);
        return postRepository.save(newPost);
    }

    public Post updatePost(int postId, String title, String content) {
        return null;
    }

    public void deletePost(int postId) {

    }

    public Optional<Post> getPost(int postId) {
        return Optional.empty();
    }

    public List<Post> getAllPosts(int page, int size) {
        return List.of();
    }

    public List<Post> getPostsByAuthor(int authorId, int page, int size) {
        return List.of();
    }

    public List<Post> searchPosts(String keyword, int page, int size) {
        return List.of();
    }

    public List<Post> searchPostsByAuthor(int authorId, String keyword, int page, int size) {
        return List.of();
    }

    public long countPostsByAuthor(int authorId) {
        return 0;
    }

    public long countAllPosts() {
        return 0;
    }
}
