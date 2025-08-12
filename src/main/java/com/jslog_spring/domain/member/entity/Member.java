package com.jslog_spring.domain.member.entity;

import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.posts = new ArrayList<>();
    }

    public static Member create(String username, String password, String name) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        return Member.builder()
                .username(username)
                .password(password)
                .name(name)
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }

    public Post createPost(String title, String content) {
        Post newPost = Post.create(this, title, content);
         this.posts.add(newPost);
         return newPost;
    }

    public void deletePost(Post post) {
        if (post == null || !this.posts.contains(post)) {
            throw new IllegalArgumentException("Post not found in member's posts.");
        }
        this.posts.remove(post);
    }
}
