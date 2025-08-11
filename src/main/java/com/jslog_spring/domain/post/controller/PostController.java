package com.jslog_spring.domain.post.controller;

import com.jslog_spring.domain.post.dto.AllPostResponseDto;
import com.jslog_spring.domain.post.dto.PostResponseDto;
import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/posts")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //이후 프로젝션 적용 고려
    @GetMapping
    public Page<AllPostResponseDto> getAllPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        return postService.getAllPosts(page, size).map(AllPostResponseDto::fromEntity);
    }

    @GetMapping("/{postId}")
    public PostResponseDto getPostById(@PathVariable("postId") Long postId) {
        Post post = postService.getPost(postId);
        return PostResponseDto.fromEntity(post);
    }

    record PostCreationRequest(Long authorId, String title, String content) {
    }

    @PostMapping
    public PostResponseDto createPost(@RequestBody PostCreationRequest request) {
        Post post = postService.createPost(request.authorId(), request.title(), request.content());
        return PostResponseDto.fromEntity(post);
    }

    record PostUpdateRequest(Long authorId, Long postId, String title, String content) {
    }

    @PutMapping
    public PostResponseDto updatePost(@RequestBody PostUpdateRequest request) {
        Post post = postService.updatePost(request.authorId(), request.postId(), request.title(), request.content());
        return PostResponseDto.fromEntity(post);
    }

    record PostDeletionRequest(Long authorId, Long postId) {
    }

    @DeleteMapping
    public String deletePost(@RequestBody PostDeletionRequest request) {
        postService.deletePost(request.authorId(), request.postId());
        return "Deleted post";
    }
}
