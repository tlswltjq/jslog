package com.jslog_spring.domain.post.entity;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    private String title;
    private String content;

    @Builder(access = AccessLevel.PRIVATE)
    private Post(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public static Post create(Member member, String title, String content) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be empty.");
        }

        return Post.builder()
                .member(member)
                .title(title)
                .content(content)
                .build();
    }

    public void update(String title, String content) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        if (this.id == null || post.id == null) {
            return false;
        }
        return this.id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }
}
