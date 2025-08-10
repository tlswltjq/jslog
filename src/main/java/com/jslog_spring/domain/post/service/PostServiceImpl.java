package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public Post createPost(Long authorId, String title, String content) {
        Post newPost = Post.create(authorId, title, content);
        return postRepository.save(newPost);
    }

    public Post updatePost(Long authorId, Long postId, String title, String content) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));

        if (!post.getAuthorId().equals(authorId)) {
            throw new NoSuchElementException("Post not found or author does not match");
        }
        post.update(title, content);

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long authorId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + postId));

        if (!post.getAuthorId().equals(authorId)) {
            //TODO : 에러타입 변경
            throw new NoSuchElementException("Post not found or author does not match");
        }

        postRepository.delete(post);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(()-> new NoSuchElementException("Post not found"));
    }

    public Page<Post> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable);
    }

    public Page<Post> getPostsByAuthor(Long authorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByAuthorId(authorId, pageable);
    }

    public Page<Post> searchPosts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
    }

    public Page<Post> searchPostsByAuthor(Long authorId, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByAuthorIdAndKeyword(authorId, keyword, pageable);
    }
}
