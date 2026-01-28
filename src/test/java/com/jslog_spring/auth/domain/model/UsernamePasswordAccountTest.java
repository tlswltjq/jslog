package com.jslog_spring.auth.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsernamePasswordAccountTest {

    @Test
    @DisplayName("계정 생성 테스트")
    void createAccount() {
        UsernamePasswordAccount account = UsernamePasswordAccount.of(1L, "user", "encodedPwd");

        assertThat(account.getMemberId()).isEqualTo(1L);
        assertThat(account.getUsername()).isEqualTo("user");
        assertThat(account.getPassword()).isEqualTo("encodedPwd");
        assertThat(account.getAuthProvider()).isEqualTo(AuthProvider.EMAIL);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
    void changePassword() {
        UsernamePasswordAccount account = UsernamePasswordAccount.of(1L, "user", "encodedPwd");
        String newPwd = "newEncodedPwd";

        account.changePassword(newPwd);

        assertThat(account.getPassword()).isEqualTo(newPwd);
    }
}
