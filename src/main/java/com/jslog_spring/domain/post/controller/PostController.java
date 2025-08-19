package com.jslog_spring.domain.post.controller;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.post.dto.AllPostResponseDto;
import com.jslog_spring.domain.post.dto.PostResponseDto;
import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public PostResponseDto getPostById(@PathVariable("postId") Long postId) {
        Post post = postService.getPost(postId);
        return PostResponseDto.fromEntity(post);
    }

    record PostCreationRequest(String title, String content) {
    }

    @PostMapping
    public PostResponseDto createPost(@AuthenticationPrincipal Member member, @RequestBody PostCreationRequest request) {
        Post post = postService.createPost(member, request.title(), request.content());
        return PostResponseDto.fromEntity(post);
    }

    record PostUpdateRequest(Long postId, String title, String content) {
    }

    @PutMapping
    public PostResponseDto updatePost(@AuthenticationPrincipal Member member, @RequestBody PostUpdateRequest request) {
        Post post = postService.updatePost(member, request.postId(), request.title(), request.content());
        return PostResponseDto.fromEntity(post);
    }

    record PostDeletionRequest(Long postId) {
    }

    @DeleteMapping
    public String deletePost(@AuthenticationPrincipal Member member, @RequestBody PostDeletionRequest request) {
        postService.deletePost(member, request.postId());
        return "Deleted post";
    }
}
