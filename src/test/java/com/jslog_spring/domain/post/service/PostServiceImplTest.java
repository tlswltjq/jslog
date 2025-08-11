package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    @DisplayName("작성자id, 제목, 내용으로 게시글 엔티티를 생성해낸다.")
    void postCreationTest() {
        Long authorId = 1L;
        String title = "Test Title";
        String content = "Test Content";

        Post postToSave = Post.create(authorId, title, content);
        when(postRepository.save(any(Post.class))).thenReturn(postToSave);

        Post createdPost = postService.createPost(authorId, title, content);

        assertNotNull(createdPost);
        assertEquals(authorId, createdPost.getAuthorId());
        assertEquals(title, createdPost.getTitle());
        assertEquals(content, createdPost.getContent());
    }

    @Test
    @DisplayName("요청자의 id와 게시글 id이 게시글의 authorId와 postId가 일치하는 경우 게시글을 수정한다.")
    void postUpdateTest() {
        Long authorId = 1L;
        String title = "Test Title";
        String content = "Test Content";

        Post post = Post.create(authorId, title, content);
        when(postRepository.findById(post.getId())).thenReturn(java.util.Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post updated = postService.updatePost(1L, post.getId(), "Updated Title", "Updated Content");

        assertNotNull(updated);
        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated Content", updated.getContent());
    }

    @Test
    @DisplayName("수정하려는 게시글이 존재하지 않는다면 예외를 발생시킨다.")
    void postUpdateTest_NotFound() {
        Long authorId = 1L;
        Long postId = 999L;

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            postService.updatePost(authorId, postId, "Updated Title", "Updated Content");
        });
    }
    @Test
    @DisplayName("작성자와 authorId가 다르면 예외를 발생시킨다.")
    void postUpdateTest_AuthorMismatch() {
        Long requestingAuthorId = 1L;
        Long originalAuthorId = 2L;
        Long postId = 1L;

        Post postByOther = Post.create(originalAuthorId, "Test Title", "Test Content");

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(postByOther));

        assertThrows(NoSuchElementException.class, () -> {
            postService.updatePost(requestingAuthorId, postId, "Updated Title", "Updated Content");
        });
    }
}