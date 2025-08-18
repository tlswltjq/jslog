package com.jslog_spring.domain.post;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.post.entity.Post;
import fixture.MemberFixture;
import fixture.PostFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {
    @Test
    @DisplayName("authorId, title, content를 입력받아 Post 객체를 생성한다.")
    void postCreate() {
        Member member = MemberFixture.createMemberWithId(1L);
        Post post = PostFixture.createPost(member);

        assertThat(post).isNotNull();
        assertThat(post.getMember()).isEqualTo(member);
        assertThat(post.getTitle()).isEqualTo("Test Title");
        assertThat(post.getContent()).isEqualTo("Test Content");
    }

    @Test
    @DisplayName("title, content를 입력받아 Post 객체를 수정한다.")
    void postUpdate() {
        String newTitle = "updated title";
        String newContent = "updated content";

        Member member = MemberFixture.createMemberWithId(1L);
        Post post = PostFixture.createPost(member);

        post.update(newTitle, newContent);

        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(newContent);
    }

}
