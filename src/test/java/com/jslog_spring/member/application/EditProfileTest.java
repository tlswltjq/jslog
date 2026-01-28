package com.jslog_spring.member.application;

import com.jslog_spring.member.application.dto.ProfileEditCommand;
import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.model.MemberType;
import com.jslog_spring.member.domain.policy.ProfileEditPolicy;
import com.jslog_spring.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditProfileTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ProfileEditPolicy profileEditPolicy;

    private EditProfile editProfile;

    @BeforeEach
    void setUp() {
        editProfile = new EditProfile(java.util.List.of(profileEditPolicy), memberRepository);
    }

    @Test
    @DisplayName("프로필(Bio) 수정 테스트")
    void editBio() {
        // given
        Long memberId = 1L;
        String newBio = "New Bio";
        Member member = Member.of("nick", MemberType.USER);

        when(memberRepository.findById(memberId)).thenReturn(member);

        // when
        Long resultId = editProfile.invoke(memberId, newBio);

        // then
        assertThat(resultId).isEqualTo(memberId);
        assertThat(member.getBio()).isEqualTo(newBio);
        verify(profileEditPolicy).validate(eq(member), any(ProfileEditCommand.class));
    }
}
