package com.jslog_spring.member.application;

import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.model.MemberType;
import com.jslog_spring.member.domain.policy.NicknameChangePolicy;
import com.jslog_spring.member.domain.repository.MemberQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChangeNicknameTest {

    @Mock
    private MemberQueryRepository memberRepository;
    @Mock
    private NicknameChangePolicy nicknameChangePolicy;

    private ChangeNickname changeNickname;

    @BeforeEach
    void setUp() {
        changeNickname = new ChangeNickname(java.util.List.of(nicknameChangePolicy), memberRepository);
    }

    @Test
    @DisplayName("닉네임 변경 테스트")
    void changeNickname() {
        // given
        Long memberId = 1L;
        String newNickname = "new_nick";
        Member member = Member.of("old_nick", MemberType.USER);

        when(memberRepository.findById(memberId)).thenReturn(member);

        // when
        Long resultId = changeNickname.invoke(memberId, newNickname);

        // then
        assertThat(resultId).isEqualTo(memberId);
        assertThat(member.getNickname()).isEqualTo(newNickname);
        verify(nicknameChangePolicy).validate(newNickname);
    }
}
