package com.jslog_spring.domain.member.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MemberAttrTest {
    @Test
    @DisplayName("사용자 부가정보를 저장하는 엔티티 MemberAttr 생성 테스트")
    void MemberAttrCreationTest() {
        String username = "username";
        MemberAttr memberAttr = MemberAttr.create(username);

        assertThat(memberAttr).isNotNull();
        assertThat(memberAttr.getUsername()).isEqualTo(username);
        assertThat(memberAttr.getLastLoginAt()).isNotNull();
    }

    @Test
    @DisplayName("로그아웃시 마지막 접속시간이 갱신된다")
    void logoutTest() throws InterruptedException {
        String username = "username";
        MemberAttr memberAttr = MemberAttr.create(username);
        LocalDateTime beforeLogout = memberAttr.getLastLoginAt();

        Thread.sleep(1);
        memberAttr.signOut();
        assertThat(memberAttr.updateLoginTime()).isAfter(beforeLogout);
    }
}