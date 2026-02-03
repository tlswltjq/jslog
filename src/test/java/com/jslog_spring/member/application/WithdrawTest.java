package com.jslog_spring.member.application;

import com.jslog_spring.member.domain.repository.MemberCommandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WithdrawTest {

    @Mock
    private MemberCommandRepository memberRepository;

    @InjectMocks
    private Withdraw withdraw;

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void withdraw() {
        // given
        Long memberId = 1L;

        // when
        Long resultId = withdraw.invoke(memberId);

        // then
        assertThat(resultId).isEqualTo(memberId);
        verify(memberRepository).delete(memberId);
    }
}
