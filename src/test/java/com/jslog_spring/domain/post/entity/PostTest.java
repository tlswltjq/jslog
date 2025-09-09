package com.jslog_spring.domain.post.entity;

import com.jslog_spring.domain.member.entity.Member;
import fixture.MemberFixture;
import fixture.PostFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {
    @Test
    @DisplayName("사용자와 title, content를 입력받아 Post 객체를 생성한다.")
    void postCreationTest() {
        Member member = MemberFixture.createMemberWithId(1L);

        String title = "Test Title";
        String content = "Test Content";

        Post post = PostFixture.createPost(member, title, content);

        assertThat(post).isNotNull();
        assertThat(post.getMember()).isEqualTo(member);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
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
