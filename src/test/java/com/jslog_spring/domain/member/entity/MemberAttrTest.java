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
        String accessToken = "accessToken";
        MemberAttr memberAttr = MemberAttr.create(username, accessToken);

        assertThat(memberAttr).isNotNull();
        assertThat(memberAttr.getUsername()).isEqualTo(username);
        assertThat(memberAttr.getAccessToken()).isEqualTo(accessToken);
        assertThat(memberAttr.getLastLoginAt()).isNotNull();
    }

    @Test
    @DisplayName("토큰이 업데이트 될 때 마지막 로그인 시간이 갱신된다.")
    void updateAccessTokenTest() {
        String username = "username";
        String initialToken = "initialToken";
        MemberAttr memberAttr = MemberAttr.create(username, initialToken);
        String newToken = "newAccessToken";
        LocalDateTime initialTime = memberAttr.getLastLoginAt();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        memberAttr.updateAccessToken(newToken);

        assertThat(memberAttr.getAccessToken()).isEqualTo(newToken);
        assertThat(memberAttr.getLastLoginAt()).isAfter(initialTime);
    }

    @Test
    @DisplayName("로그아웃시 토큰이 null로 변경된다.")
    void logoutTest() {
        String username = "username";
        String accessToken = "accessToken";
        MemberAttr memberAttr = MemberAttr.create(username, accessToken);
        memberAttr.signOut();
        assertThat(memberAttr.getAccessToken()).isNull();
    }
}