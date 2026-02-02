package com.jslog_spring.member.domain.policy;

import com.jslog_spring.member.application.dto.ProfileEditCommand;
import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.exception.ForbiddenWordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("ForbiddenWordRule - N:M 정책 구현 테스트")
class ForbiddenWordRuleTest {

    private final ForbiddenWordRule forbiddenWordRule = new ForbiddenWordRule();

    // ============ SignUpPolicy 테스트 ============

    @Test
    @DisplayName("회원가입 시 닉네임에 금칙어가 포함되면 예외 발생")
    void validate_signUp_forbidden_nickname() {
        // given
        Member member = mock(Member.class);
        when(member.getNickname()).thenReturn("admin_user");

        // when & then
        assertThatThrownBy(() -> forbiddenWordRule.validate(member))
                .isInstanceOf(ForbiddenWordException.class);
    }

    @Test
    @DisplayName("회원가입 시 닉네임에 금칙어가 없으면 통과")
    void validate_signUp_valid_nickname() {
        // given
        Member member = mock(Member.class);
        when(member.getNickname()).thenReturn("normal_user");

        // when & then
        assertDoesNotThrow(() -> forbiddenWordRule.validate(member));
    }

    // ============ NicknameChangePolicy 테스트 ============

    @Test
    @DisplayName("닉네임 변경 시 금칙어가 포함되면 예외 발생")
    void validate_nicknameChange_forbidden() {
        // given
        String nickname = "관리자_테스트";

        // when & then
        assertThatThrownBy(() -> forbiddenWordRule.validate(nickname))
                .isInstanceOf(ForbiddenWordException.class);
    }

    @Test
    @DisplayName("닉네임 변경 시 금칙어가 없으면 통과")
    void validate_nicknameChange_valid() {
        // given
        String nickname = "일반사용자";

        // when & then
        assertDoesNotThrow(() -> forbiddenWordRule.validate(nickname));
    }

    // ============ ProfileEditPolicy 테스트 ============

    @Test
    @DisplayName("프로필 수정 시 Bio에 금칙어가 포함되면 예외 발생")
    void validate_profileEdit_forbidden_bio() {
        // given
        Member member = mock(Member.class);
        ProfileEditCommand command = new ProfileEditCommand("저는 administrator입니다");

        // when & then
        assertThatThrownBy(() -> forbiddenWordRule.validate(member, command))
                .isInstanceOf(ForbiddenWordException.class);
    }

    @Test
    @DisplayName("프로필 수정 시 Bio에 금칙어가 없으면 통과")
    void validate_profileEdit_valid_bio() {
        // given
        Member member = mock(Member.class);
        ProfileEditCommand command = new ProfileEditCommand("안녕하세요, 반갑습니다!");

        // when & then
        assertDoesNotThrow(() -> forbiddenWordRule.validate(member, command));
    }

    @Test
    @DisplayName("프로필 수정 시 Bio가 null이면 통과")
    void validate_profileEdit_null_bio() {
        // given
        Member member = mock(Member.class);
        ProfileEditCommand command = new ProfileEditCommand(null);

        // when & then
        assertDoesNotThrow(() -> forbiddenWordRule.validate(member, command));
    }
}
