package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("작성자 아이디와 게시글 아이디로 게시글을 삭제한다.")
    void postDeleteTest() {
        Long authorId = 1L;
        Long postId = 1L;

        Post postToDelete = Post.create(authorId, "Test Title", "Test Content");

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(postToDelete));

        doNothing().when(postRepository).delete(postToDelete);

        postService.deletePost(authorId, postId);

        verify(postRepository).delete(postToDelete);
    }

    @Test
    @DisplayName("작성자 아이디와 게시글 아이디로 게시글을 삭제할 때, 게시글이 존재하지 않으면 예외를 발생시킨다.")
    void postDeleteTest_NotFound() {
        Long authorId = 1L;
        Long postId = 999L;

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            postService.deletePost(authorId, postId);
        });
    }

    @Test
    @DisplayName("작성자 아이디와 게시글 아이디로 게시글을 삭제할 때, 게시글의 작성자 아이디가 일치하지 않으면 예외를 발생시킨다.")
    void postDeleteTest_AuthorMismatch() {
        Long requestingAuthorId = 1L;
        Long originalAuthorId = 2L;

        Post postByOther = Post.create(originalAuthorId, "Test Title", "Test Content");
        Long postId = postByOther.getId();


        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(postByOther));

        assertThrows(NoSuchElementException.class, () -> {
            postService.deletePost(requestingAuthorId, postId);
        });
    }

    @Test
    @DisplayName("게시글 아이디로 게시글을 조회한다.")
    void postGetTest() {
        Post post = Post.create(1L, "Test Title", "Test Content");
        Long postId = post.getId();

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(post));

        Post foundPost = postService.getPost(postId);

        assertNotNull(foundPost);
        assertEquals(postId, foundPost.getId());
        assertEquals("Test Title", foundPost.getTitle());
        assertEquals("Test Content", foundPost.getContent());
    }

    @Test
    @DisplayName("게시글 아이디로 게시글을 조회할 때, 게시글이 존재하지 않으면 예외를 발생시킨다.")
    void postGetTest_NotFound() {
        Long postId = 999L;

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            postService.getPost(postId);
        });
    }

    @Test
    @DisplayName("모든 게시글을 페이지 단위로 조회한다.")
    void getAllPostsTest() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        List<Post> postList = List.of(
                Post.create(1L, "Title 1", "Content 1"),
                Post.create(2L, "Title 2", "Content 2")
        );

        Page<Post> postPage = new PageImpl<>(postList, pageable, postList.size());

        when(postRepository.findAll(any(Pageable.class))).thenReturn(postPage);

        Page<Post> resultPage = postService.getAllPosts(page, size);

        assertNotNull(resultPage);
        assertEquals(postList.size(), resultPage.getTotalElements());
        assertEquals("Title 1", resultPage.getContent().get(0).getTitle());

        verify(postRepository).findAll(pageable);
    }

    @Test
    @DisplayName("특정 작성자의 게시글을 페이지 단위로 조회한다.")
    void getPostsByAuthorTest() {
        // given
        Long authorId = 1L;
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        List<Post> postList = List.of(
                Post.create(authorId, "Author1 Post 1", "Content 1"),
                Post.create(authorId, "Author1 Post 2", "Content 2")
        );
        Page<Post> postPage = new PageImpl<>(postList, pageable, postList.size());

        // when
        when(postRepository.findByAuthorId(authorId, pageable)).thenReturn(postPage);
        Page<Post> resultPage = postService.getPostsByAuthor(authorId, page, size);

        // then
        assertNotNull(resultPage);
        assertEquals(2, resultPage.getTotalElements());
        assertEquals("Author1 Post 1", resultPage.getContent().get(0).getTitle());
        verify(postRepository).findByAuthorId(authorId, pageable);
    }

    @Test
    @DisplayName("키워드로 게시글을 검색하여 페이지 단위로 조회한다.")
    void searchPostsTest() {
        // given
        String keyword = "search";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        List<Post> postList = List.of(
                Post.create(1L, "Title with search", "Content"),
                Post.create(2L, "Title", "Content with search keyword")
        );
        Page<Post> postPage = new PageImpl<>(postList, pageable, postList.size());

        // when
        when(postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable)).thenReturn(postPage);
        Page<Post> resultPage = postService.searchPosts(keyword, page, size);

        // then
        assertNotNull(resultPage);
        assertEquals(2, resultPage.getTotalElements());
        assertTrue(resultPage.getContent().get(0).getTitle().contains(keyword));
        verify(postRepository).findByTitleContainingOrContentContaining(keyword, keyword, pageable);
    }

    @Test
    @DisplayName("특정 작성자의 게시글을 키워드로 검색하여 페이지 단위로 조회한다.")
    void searchPostsByAuthorTest() {
        // given
        Long authorId = 1L;
        String keyword = "special";
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        List<Post> postList = List.of(
                Post.create(authorId, "A special post", "Content")
        );
        Page<Post> postPage = new PageImpl<>(postList, pageable, postList.size());

        // when
        when(postRepository.findByAuthorIdAndKeyword(authorId, keyword, pageable)).thenReturn(postPage);
        Page<Post> resultPage = postService.searchPostsByAuthor(authorId, keyword, page, size);

        // then
        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals("A special post", resultPage.getContent().get(0).getTitle());
        verify(postRepository).findByAuthorIdAndKeyword(authorId, keyword, pageable);
    }
}