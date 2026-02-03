package com.jslog_spring.member.application;

import com.jslog_spring.auth.domain.model.AuthProvider;
import com.jslog_spring.auth.domain.model.UsernamePasswordAccount;
import com.jslog_spring.auth.domain.policy.AccountCreationPolicy;
import com.jslog_spring.auth.domain.service.AccountManager;
import com.jslog_spring.member.application.dto.SignUpResult;
import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.policy.SignUpPolicy;
import com.jslog_spring.member.domain.repository.MemberCommandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignUpTest {

    @Mock
    private MemberCommandRepository memberRepository;
    @Mock
    private AccountManager accountManager;
    @Mock
    private SignUpPolicy memberPolicy;
    @Mock
    private AccountCreationPolicy accountPolicy;

    private SignUp signUp;

    @BeforeEach
    void setUp() {
        signUp = new SignUp(
                List.of(memberPolicy),
                List.of(accountPolicy),
                memberRepository,
                accountManager);
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUpSuccess() {
        // given
        String nickname = "testUser";
        String email = "test@example.com";
        String password = "password";

        // We can't easily mock the exact instance created inside invoke, so we match
        // any.

        // Mock MemberRepository save
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member m = invocation.getArgument(0);
            // Simulate saving by potentially assuming ID is set, or just return it.
            // But we can't set ID on protected constructor/field easily without reflection
            // or just verify flow.
            // Let's assume the repository returns the object passed.
            // To be more realistic, we might want to mock the ID.
            return m;
        });

        // Mock AccountManager
        UsernamePasswordAccount account = UsernamePasswordAccount.builder()
                .memberId(1L)
                .username(email)
                .authProvider(AuthProvider.EMAIL)
                .build();
        when(accountManager.createUsernamePasswordAccount(any(), anyString(), anyString())).thenReturn(account);
        when(accountPolicy.supports(any())).thenReturn(true);

        // when
        SignUpResult result = signUp.invoke(nickname, email, password);

        // then
        verify(memberPolicy).validate(any(Member.class));
        verify(accountPolicy).validate(any());
        verify(memberRepository).save(any(Member.class));
        verify(accountManager).saveAccount(any(UsernamePasswordAccount.class));

        assertThat(result.nickname()).isEqualTo(nickname);
        assertThat(result.username()).isEqualTo(email);
    }
}
