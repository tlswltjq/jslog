package com.jslog_spring.member.application;

import com.jslog_spring.member.application.dto.MemberInfo;
import com.jslog_spring.member.domain.model.MemberType;
import com.jslog_spring.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMemberListTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private GetMemberList getMemberList;

    @Test
    @DisplayName("멤버 목록 조회 테스트")
    void getMemberList() {
        // given
        MemberInfo info1 = new MemberInfo("nick1", MemberType.USER, "bio1", LocalDateTime.now());
        MemberInfo info2 = new MemberInfo("nick2", MemberType.USER, "bio2", LocalDateTime.now());
        List<MemberInfo> expectedList = List.of(info1, info2);

        when(memberRepository.findAllMemberInfos()).thenReturn(expectedList);

        // when
        List<MemberInfo> result = getMemberList.invoke();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expectedList);
    }
}
