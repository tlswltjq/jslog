package com.jslog_spring.domain.post;

import com.jslog_spring.domain.post.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {
    @Test
    @DisplayName("authorId, title, content를 입력받아 Post 객체를 생성한다.")
    void postCreate() {
        long authorId = 1L;
        String title = "test title";
        String content = "test content";

        Post post = Post.create(authorId, title, content);

        assertThat(post).isNotNull();
        assertThat(post.getAuthorId()).isEqualTo(authorId);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("title, content를 입력받아 Post 객체를 수정한다.")
    void postUpdate() {
        long authorId = 1L;
        String title = "test title";
        String content = "test content";
        String newTitle = "updated title";
        String newContent = "updated content";

        Post post = Post.create(authorId, title, content);

        post.update(newTitle, newContent);

        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(newContent);
    }

}
