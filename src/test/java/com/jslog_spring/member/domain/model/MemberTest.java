package com.jslog_spring.member.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    @DisplayName("회원 생성 테스트")
    void createMember() {
        Member member = Member.of("nickname", MemberType.USER);

        assertThat(member.getNickname()).isEqualTo("nickname");
        assertThat(member.getType()).isEqualTo(MemberType.USER);
        assertThat(member.getBio()).isEqualTo("");
    }

    @Test
    @DisplayName("닉네임 변경 테스트")
    void changeNickname() {
        Member member = Member.of("nickname", MemberType.USER);
        String newNickname = "new_nickname";

        member.changeNickname(newNickname);

        assertThat(member.getNickname()).isEqualTo(newNickname);
    }

    @Test
    @DisplayName("자기소개 변경 테스트")
    void changeBio() {
        Member member = Member.of("nickname", MemberType.USER);
        String newBio = "Hello World";

        member.changeBio(newBio);

        assertThat(member.getBio()).isEqualTo(newBio);
    }
}
