package com.jslog_spring.domain.post.controller;

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


    @GetMapping
    public Page<Post> getAllPosts(@RequestParam (value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return postService.getAllPosts(page, size);
    }

    record PostCreationRequest(Long authorId, String title, String content) {}
    @PostMapping
    public PostResponseDto createPost(@RequestBody PostCreationRequest request) {
        Post post = postService.createPost(request.authorId(), request.title(), request.content());
        return new PostResponseDto(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getAuthorId(),
            post.getCreateDate().toString(),
            post.getModifyDate().toString()
        );
    }
}
