package com.jslog_spring.member.domain.policy;

import com.jslog_spring.member.application.dto.ProfileEditCommand;
import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.repository.MemberRepository;
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
    private MemberRepository memberRepository;

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
    @DisplayName("프로필 수정 시 닉네임 변경 없음")
    void validate_update_no_change() {
        // given
        Member member = mock(Member.class);
        String currentNickname = "old";
        when(member.getNickname()).thenReturn(currentNickname);
        ProfileEditCommand command = new ProfileEditCommand(currentNickname, null);

        // when
        nicknameRule.validate(member, command);

        // then
        // 닉네임 중복 체크가 호출되지 않아야 함
        verify(memberRepository, never()).existsByNickname(any());
    }

    @Test
    @DisplayName("프로필 수정 시 닉네임 변경 및 중복 발생")
    void validate_update_change_duplication() {
        // given
        Member member = mock(Member.class);
        String oldNickname = "old";
        String newNickname = "duplicate";

        when(member.getNickname()).thenReturn(oldNickname);

        ProfileEditCommand command = new ProfileEditCommand(newNickname, null);

        when(memberRepository.existsByNickname(newNickname)).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> nicknameRule.validate(member, command))
                .isInstanceOf(NicknameDuplicationException.class);
    }

    @Test
    @DisplayName("프로필 수정 시 닉네임 변경 및 중복 없음")
    void validate_update_change_success() {
        // given
        Member member = mock(Member.class);
        String oldNickname = "old";
        String newNickname = "unique";

        when(member.getNickname()).thenReturn(oldNickname);

        ProfileEditCommand command = new ProfileEditCommand(newNickname, null);

        when(memberRepository.existsByNickname(newNickname)).thenReturn(false);

        // when & then
        nicknameRule.validate(member, command); // Should not throw
    }
}
