package com.jslog_spring.member.domain.policy;

import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.model.MemberType;
import com.jslog_spring.member.domain.repository.MemberRepository;
import com.jslog_spring.member.exception.NicknameDuplicationException;
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
class NicknameDuplicationPolicyTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private NicknameDuplicationPolicy nicknameDuplicationPolicy;

    @Test
    @DisplayName("닉네임 중복 검증 - 중복없음")
    void validateSuccess() {
        // given
        Member member = Member.of("unique_nick", MemberType.USER);
        when(memberRepository.existsByNickname(anyString())).thenReturn(false);

        // when
        nicknameDuplicationPolicy.validate(member);

        // then
        verify(memberRepository).existsByNickname("unique_nick");
    }

    @Test
    @DisplayName("닉네임 중복 검증 - 중복발생")
    void validateFail() {
        // given
        Member member = Member.of("duplicate_nick", MemberType.USER);
        when(memberRepository.existsByNickname("duplicate_nick")).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> nicknameDuplicationPolicy.validate(member))
                .isInstanceOf(NicknameDuplicationException.class);
    }
}
