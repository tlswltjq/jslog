package com.jslog_spring.auth.domain.service;

import com.jslog_spring.auth.domain.model.AuthProvider;
import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountManagerTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountManager accountManager;

    @Test
    @DisplayName("계정 생성 및 패스워드 인코딩 테스트")
    void createAccount() {
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        UsernamePasswordAccount account = accountManager.createUsernamePasswordAccount(1L, "email", "raw");

        assertThat(account.getPassword()).isEqualTo("encoded");
        assertThat(account.getMemberId()).isEqualTo(1L);
        assertThat(account.getAuthProvider()).isEqualTo(AuthProvider.EMAIL);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
    void changePassword() {
        UsernamePasswordAccount account = UsernamePasswordAccount.of(1L, "email", "old");
        when(passwordEncoder.encode("new")).thenReturn("new_encoded");

        accountManager.changePassword(account, "new");

        assertThat(account.getPassword()).isEqualTo("new_encoded");
    }

    @Test
    @DisplayName("비밀번호 확인 테스트")
    void checkPassword() {
        UsernamePasswordAccount account = UsernamePasswordAccount.of(1L, "email", "encoded");
        when(passwordEncoder.matches("raw", "encoded")).thenReturn(true);

        boolean result = accountManager.checkPassword(account, "raw");

        assertThat(result).isTrue();
    }
}
