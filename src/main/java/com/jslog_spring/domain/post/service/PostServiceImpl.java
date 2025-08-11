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

    public Post createPost(Long authorId, String title, String content) {
        Post newPost = Post.create(authorId, title, content);
        return postRepository.save(newPost);
    }

    public Post updatePost(Long postId, String title, String content) {
        return null;
    }

    public void deletePost(Long postId) {

    }

    public Optional<Post> getPost(Long postId) {
        return Optional.empty();
    }

    public List<Post> getAllPosts(int page, int size) {
        return List.of();
    }

    public List<Post> getPostsByAuthor(Long authorId, int page, int size) {
        return List.of();
    }

    public List<Post> searchPosts(String keyword, int page, int size) {
        return List.of();
    }

    public List<Post> searchPostsByAuthor(Long authorId, String keyword, int page, int size) {
        return List.of();
    }

    public long countPostsByAuthor(Long authorId) {
        return 0;
    }

    public long countAllPosts() {
        return 0;
    }


}
