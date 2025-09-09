package com.jslog_spring.domain.post.entity;

import com.jslog_spring.domain.member.entity.Member;
import fixture.MemberFixture;
import fixture.PostFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @DisplayName("Post 객체 생성시 사용자가 null 이면 예외가 발생한다.")
    void postCreationWithNullMember() {
        String title = "Test Title";
        String content = "Test Content";

        assertThatThrownBy(() -> {
            PostFixture.createPost(null, title, content);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Member cannot be null.");
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

    @Test
    @DisplayName(" Post 객체 수정시 title, content중 null인 인자는 업데이트 되지 않는다.")
    void postUpdateWithNull() {
        String originalTitle = "original title";
        String originalContent = "original content";

        Member member = MemberFixture.createMemberWithId(1L);
        Post post = PostFixture.createPost(member, originalTitle, originalContent);

        post.update(null, null);

        assertThat(post.getTitle()).isEqualTo(originalTitle);
        assertThat(post.getContent()).isEqualTo(originalContent);
    }

    @Test
    @DisplayName("Post 객체 수정시 title, content중 빈 문자열인 인자는 업데이트 되지 않는다.")
    void postUpdateWithBlank() {
        String originalTitle = "original title";
        String originalContent = "original content";

        Member member = MemberFixture.createMemberWithId(1L);
        Post post = PostFixture.createPost(member, originalTitle, originalContent);

        post.update("", "");

        assertThat(post.getTitle()).isEqualTo(originalTitle);
        assertThat(post.getContent()).isEqualTo(originalContent);
    }

}
