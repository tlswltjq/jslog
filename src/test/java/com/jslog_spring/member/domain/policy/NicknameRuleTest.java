package com.jslog_spring.member.domain.policy;

import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.repository.MemberQueryRepository;
import com.jslog_spring.member.exception.NicknameDuplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NicknameRuleTest {

    @Mock
    private MemberQueryRepository memberRepository;

    @InjectMocks
    private NicknameRule nicknameRule;

    @Test
    @DisplayName("회원가입 시 닉네임 중복 발생")
    void validate_signup_duplication() {
        // given
        Member member = mock(Member.class);
        String nickname = "duplicate";
        when(member.getNickname()).thenReturn(nickname);
        when(memberRepository.existsByNickname(nickname)).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> nicknameRule.validate(member))
                .isInstanceOf(NicknameDuplicationException.class);
    }

    @Test
    @DisplayName("회원가입 시 닉네임 중복 없음")
    void validate_signup_success() {
        // given
        Member member = mock(Member.class);
        String nickname = "unique";
        when(member.getNickname()).thenReturn(nickname);
        when(memberRepository.existsByNickname(nickname)).thenReturn(false);

        // when & then
        nicknameRule.validate(member); // Should not throw
    }

    @Test
    @DisplayName("닉네임 변경 시 중복 발생")
    void validate_nicknameChange_duplication() {
        // given
        String nickname = "duplicate";
        when(memberRepository.existsByNickname(nickname)).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> nicknameRule.validate(nickname))
                .isInstanceOf(NicknameDuplicationException.class);
    }

    @Test
    @DisplayName("닉네임 변경 시 중복 없음")
    void validate_nicknameChange_success() {
        // given
        String nickname = "unique";
        when(memberRepository.existsByNickname(nickname)).thenReturn(false);

        // when & then
        nicknameRule.validate(nickname); // Should not throw
    }
}
