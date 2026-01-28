package com.jslog_spring.member.application;

import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.model.MemberType;
import com.jslog_spring.member.domain.policy.MemberPolicy;
import com.jslog_spring.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditProfileTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberPolicy memberPolicy;

    private EditProfile editProfile;

    @BeforeEach
    void setUp() {
        editProfile = new EditProfile(List.of(memberPolicy), memberRepository);
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
        verify(memberPolicy).validate(member);
    }
}
