package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.post.repository.PostRepository;
import com.jslog_spring.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final Rq rq;
}
