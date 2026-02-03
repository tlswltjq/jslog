package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.application.dto.BacklogInfo;
import com.jslog_spring.backlog.domain.repository.BacklogQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetInfoOfBacklogTest {

    @Mock
    private BacklogQueryRepository backlogRepository;

    @InjectMocks
    private GetInfoOfBacklog getInfoOfBacklog;

    @Test
    @DisplayName("백로그 상세 정보 조회")
    void invoke() {
        // given
        Long userId = 1L;
        Long backlogId = 100L;
        BacklogInfo info = new BacklogInfo(backlogId, userId, "Title", "Desc", LocalDateTime.now(), false,
                LocalDateTime.now(), LocalDateTime.now());

        given(backlogRepository.findInfoByIdAndOwner(backlogId, userId)).willReturn(info);

        // when
        BacklogInfo result = getInfoOfBacklog.invoke(userId, backlogId);

        // then
        assertThat(result.id()).isEqualTo(backlogId);
        assertThat(result.ownedBy()).isEqualTo(userId);
    }
}
