package com.jslog_spring.member.application;

import com.jslog_spring.member.application.dto.MemberInfo;
import com.jslog_spring.member.domain.model.MemberType;
import com.jslog_spring.member.domain.repository.MemberQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMemberInfoTest {

    @Mock
    private MemberQueryRepository memberRepository;

    @InjectMocks
    private GetMemberInfo getMemberInfo;

    @Test
    @DisplayName("멤버 정보 조회 테스트")
    void getMemberInfo() {
        // given
        Long memberId = 1L;
        MemberInfo expectedInfo = new MemberInfo("nick", MemberType.USER, "bio", LocalDateTime.now());
        when(memberRepository.findMemberInfoById(memberId)).thenReturn(expectedInfo);

        // when
        MemberInfo result = getMemberInfo.invoke(memberId);

        // then
        assertThat(result).isEqualTo(expectedInfo);
    }
}
