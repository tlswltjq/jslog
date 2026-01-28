package com.jslog_spring.auth.domain.policy;

import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import com.jslog_spring.auth.domain.repository.UsernamePasswordAccountRepository;
import com.jslog_spring.auth.exception.UsernameDuplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsernameRuleTest {

    @Mock
    private UsernamePasswordAccountRepository accountRepository;

    @InjectMocks
    private UsernameRule usernameRule;

    @Test
    @DisplayName("유저네임(이메일) 중복 검증 - 중복없음")
    void validateSuccess() {
        UsernamePasswordAccount account = UsernamePasswordAccount.of(1L, "unique", "pwd");
        when(accountRepository.existsByUsername(anyString())).thenReturn(false);

        usernameRule.validate(account);

        verify(accountRepository).existsByUsername("unique");
    }

    @Test
    @DisplayName("유저네임(이메일) 중복 검증 - 중복발생")
    void validateFail() {
        UsernamePasswordAccount account = UsernamePasswordAccount.of(1L, "dup", "pwd");
        when(accountRepository.existsByUsername("dup")).thenReturn(true);

        assertThatThrownBy(() -> usernameRule.validate(account))
                .isInstanceOf(UsernameDuplicationException.class);
    }
}
