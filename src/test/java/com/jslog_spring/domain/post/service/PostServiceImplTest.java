package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.repository.MemberRepository;
import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.domain.post.repository.PostRepository;
import fixture.MemberFixture;
import fixture.PostFixture;
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
import java.util.Optional;

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
    @DisplayName("작성자 객체, 제목, 내용으로 게시글 엔티티를 생성해낸다.")
    void postCreationTest() {
        Member member = MemberFixture.createMemberWithId(1L);
        String title = "Test Title";
        String content = "Test Content";

        Post postToSave = PostFixture.createPost(member);

        when(postRepository.save(any(Post.class))).thenReturn(postToSave);

        Post createdPost = postService.createPost(member, title, content);

        assertNotNull(createdPost);
        assertEquals(member.getId(), createdPost.getMember().getId());
        assertEquals(title, createdPost.getTitle());
        assertEquals(content, createdPost.getContent());
    }

    @Test
    @DisplayName("요청자 객체와 게시글 id가 게시글의 author와 postId가 일치하는 경우 게시글을 수정한다.")
    void postUpdateTest() {
        Long postId = 1L;
        Member member = MemberFixture.createMemberWithId(1L);
        Post post = PostFixture.createPostWithId(postId, member);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.updatePost(member, postId, "Updated Title", "Updated Content");

        verify(postRepository).save(post);
        assertEquals("Updated Title", post.getTitle());
        assertEquals("Updated Content", post.getContent());
    }

    @Test
    @DisplayName("수정하려는 게시글이 존재하지 않는다면 예외를 발생시킨다.")
    void postUpdateTest_NotFound() {
        Long postId = 999L;
        Member dummyMember = MemberFixture.createMemberWithId(1L);

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            postService.updatePost(dummyMember, postId, "Updated Title", "Updated Content");
        });
    }

    @Test
    @DisplayName("요청자 객체와 게시글의 author가 다르면 예외를 발생시킨다.")
    void postUpdateTest_AuthorMismatch() {
        Member requestingMember = MemberFixture.createMemberWithId(1L);
        Member originalMember = MemberFixture.createMemberWithId(2L);
        Long postId = 1L;

        Post post = PostFixture.createPostWithId(postId, originalMember);

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(post));

        assertThrows(NoSuchElementException.class, () -> {
            postService.updatePost(requestingMember, postId, "Test Title", "Test Content");
        });
    }

    @Test
    @DisplayName("작성자 객체와 게시글 아이디로 게시글을 삭제한다.")
    void postDeleteTest() {
        Member member = MemberFixture.createMemberWithId(1L);
        Long postId = 1L;

        Post post = PostFixture.createPostWithId(postId, member);

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(post));
        doNothing().when(postRepository).delete(any(Post.class));

        postService.deletePost(member, postId);

        verify(postRepository).delete(any(Post.class));
    }

    @Test
    @DisplayName("작성자 객체와 게시글 아이디로 게시글을 삭제할 때, 게시글이 존재하지 않으면 예외를 발생시킨다.")
    void postDeleteTest_NotFound() {
        Long postId = 999L;
        Member dummyMember = MemberFixture.createMemberWithId(1L);

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            postService.deletePost(dummyMember, postId);
        });
    }

    @Test
    @DisplayName("작성자 객체와 게시글 아이디로 게시글을 삭제할 때, 게시글의 작성자 객체가 일치하지 않으면 예외를 발생시킨다.")
    void postDeleteTest_AuthorMismatch() {
        Member requestingMember = MemberFixture.createMemberWithId(1L);
        Member originalMember = MemberFixture.createMemberWithId(2L);
        Long postId = 1L;

        Post post = PostFixture.createPostWithId(postId, originalMember);

        when(postRepository.findById(postId)).thenReturn(java.util.Optional.of(post));

        assertThrows(NoSuchElementException.class, () -> {
            postService.deletePost(requestingMember, postId);
        });
    }

    @Test
    @DisplayName("게시글 아이디로 게시글을 조회한다.")
    void postGetTest() {
        Member member = MemberFixture.createMemberWithId(1L);
        Long postId = 1L;

        Post post = PostFixture.createPostWithId(postId, member, "Test Title", "Test Content");

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

        Member member1 = MemberFixture.createMemberWithId(1L);
        Member member2 = MemberFixture.createMemberWithId(2L);

        List<Post> postList = List.of(
                PostFixture.createPost(member1, "Title 1", "Content 1"),
                PostFixture.createPost(member2, "Title 2", "Content 2")
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
    @DisplayName("특정 작성자 객체의 게시글을 페이지 단위로 조회한다.")
    void getPostsByAuthorTest() {
        Member member = MemberFixture.createMemberWithId(1L);
        
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        List<Post> postList = List.of(
                PostFixture.createPost(member, "Author1 Post 1", "Content 1"),
                PostFixture.createPost(member, "Author1 Post 2", "Content 2")
        );
        Page<Post> postPage = new PageImpl<>(postList, pageable, postList.size());

        when(postRepository.findByMember(member, pageable)).thenReturn(postPage);
        Page<Post> resultPage = postService.getPostsByAuthor(member, page, size);

        assertNotNull(resultPage);
        assertEquals(2, resultPage.getTotalElements());
        assertEquals("Author1 Post 1", resultPage.getContent().get(0).getTitle());
        verify(postRepository).findByMember(member, pageable);
    }

    @Test
    @DisplayName("키워드로 게시글을 검색하여 페이지 단위로 조회한다.")
    void searchPostsTest() {
        String keyword = "search";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Member member1 = MemberFixture.createMemberWithId(1L);
        Member member2 = MemberFixture.createMemberWithId(2L);

        List<Post> postList = List.of(
                PostFixture.createPost(member1, "Title with search 1", "Content 1"),
                PostFixture.createPost(member2, "Title with search 2", "Content 2")
        );
        Page<Post> postPage = new PageImpl<>(postList, pageable, postList.size());

        when(postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable)).thenReturn(postPage);
        Page<Post> resultPage = postService.searchPosts(keyword, page, size);

        assertNotNull(resultPage);
        assertEquals(2, resultPage.getTotalElements());
        assertTrue(resultPage.getContent().get(0).getTitle().contains(keyword));
        verify(postRepository).findByTitleContainingOrContentContaining(keyword, keyword, pageable);
    }

    @Test
    @DisplayName("특정 작성자 객체의 게시글을 키워드로 검색하여 페이지 단위로 조회한다.")
    void searchPostsByAuthorTest() {
        Member member = MemberFixture.createMemberWithId(1L);
        String keyword = "special";
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);

        List<Post> postList = List.of(
                PostFixture.createPost(member, "A special post", "Content")
        );
        Page<Post> postPage = new PageImpl<>(postList, pageable, postList.size());

        when(postRepository.findByMemberAndKeyword(member, keyword, pageable)).thenReturn(postPage);
        Page<Post> resultPage = postService.searchPostsByAuthor(member, keyword, page, size);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        assertEquals("A special post", resultPage.getContent().get(0).getTitle());
        verify(postRepository).findByMemberAndKeyword(member, keyword, pageable);
    }
}