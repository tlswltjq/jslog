package com.jslog_spring.domain.post.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.repository.MemberRepository;
import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.domain.post.repository.PostRepository;
import com.jslog_spring.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Post createPost(Member author, String title, String content) {
        Post newPost = Post.create(author, title, content);
        return postRepository.save(newPost);
    }

    public Post updatePost(Member author, Long postId, String title, String content) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));

        if (!post.getMember().equals(author)) {
            throw new NoSuchElementException("Post not found or author does not match");
        }
        post.update(title, content);

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Member author, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + postId));

        if (!post.getMember().getId().equals(author.getId())) {
            //NOTE : 에러타입 변경할것
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

    public Page<Post> getPostsByAuthor(Member author, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByMember(author, pageable);
    }

    public Page<Post> searchPosts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
    }

    public Page<Post> searchPostsByAuthor(Member author, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByMemberAndKeyword(author, keyword, pageable);
    }
}