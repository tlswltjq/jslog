package com.jslog_spring.domain.post.controller;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.post.dto.AllPostResponseDto;
import com.jslog_spring.domain.post.dto.PostResponseDto;
import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.domain.post.exception.PostNotFoundException;
import com.jslog_spring.domain.post.service.PostService;
import com.jslog_spring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RequestMapping("/api/posts")
@RestController
@RequiredArgsConstructor
    //TODO : ApiResponse로 교체
public class PostController {
    private final PostService postService;

    //이후 프로젝션 적용 고려
    @GetMapping
    public Page<AllPostResponseDto> getAllPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Get all posts");
        return postService.getAllPosts(page, size).map(AllPostResponseDto::fromEntity);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto> getPostById(@PathVariable("postId") Long postId) {
        try {
            Post post = postService.getPost(postId);
            PostResponseDto response = PostResponseDto.fromEntity(post);
            return ApiResponse.success("200", "포스트 조회 성공", response);
        } catch (PostNotFoundException e) {
            log.warn("Post not found: {}", postId);
            return ApiResponse.failure("404", e.getMessage());
        } catch (Exception e) {
            log.warn("Error retrieving post: {}", postId);
            return ApiResponse.failure("500", "서버 오류가 발생했습니다.");
        }
    }

    record PostCreationRequest(String title, String content) {
    }

    @PostMapping
    public ApiResponse<PostResponseDto> createPost(@AuthenticationPrincipal Member member, @RequestBody PostCreationRequest request) {
        Post post = postService.createPost(member, request.title(), request.content());
        PostResponseDto response = PostResponseDto.fromEntity(post);
        return ApiResponse.success("201", "포스트 생성 성공", response);
    }

    record PostUpdateRequest(Long postId, String title, String content) {
    }

    @PutMapping
    public ApiResponse<PostResponseDto> updatePost(@AuthenticationPrincipal Member member, @RequestBody PostUpdateRequest request) {
        try {
            Post post = postService.updatePost(member, request.postId(), request.title(), request.content());
            PostResponseDto response = PostResponseDto.fromEntity(post);
            return ApiResponse.success("200", "포스트 수정 성공", response);
        } catch (NoSuchElementException | PostNotFoundException e) {
            return ApiResponse.failure("404", e.getMessage());
        }
    }

    record PostDeletionRequest(Long postId) {
    }

    @DeleteMapping
    public ApiResponse deletePost(@AuthenticationPrincipal Member member, @RequestBody PostDeletionRequest request) {
        try {
        postService.deletePost(member, request.postId());
        return ApiResponse.success("204", "포스트 삭제 성공");
        } catch (NoSuchElementException | PostNotFoundException e) {
            return ApiResponse.failure("404", e.getMessage());
        }
    }
}
