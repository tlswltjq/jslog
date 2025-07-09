package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.domain.post.repository.PostRepository;
import com.jslog_spring.global.rq.Rq;
import com.jslog_spring.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final Rq rq;

//    @Transactional
//    public RsData<Post> create(Member author, String title, String contentMarkdown, boolean isPublished) {
//        String slug = title.toLowerCase().replace(" ", "-"); // 간단한 slug 생성
//        String description = contentMarkdown.length() > 150 ? contentMarkdown.substring(0, 150) : contentMarkdown;
//
//        Post post = Post.builder()
//                .author(author)
//                .title(title)
//                .slug(slug)
//                .contentMarkdown(contentMarkdown)
//                .description(description)
//                .isPublished(isPublished)
//                .build();
//
//        postRepository.save(post);
//
//        return RsData.of("S-1", "게시글이 성공적으로 등록되었습니다.", post);
//    }
}
