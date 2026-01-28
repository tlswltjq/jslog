package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogRepository;
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
class DoneBacklogTest {

    @Mock
    private BacklogRepository backlogRepository;

    @InjectMocks
    private DoneBacklog doneBacklog;

    @Test
    @DisplayName("백로그 완료 처리 성공")
    void invoke() {
        // given
        Long userId = 1L;
        Long backlogId = 1L;
        Backlog backlog = Backlog.of(userId, "할 일", "설명", LocalDateTime.now());

        given(backlogRepository.findById(backlogId)).willReturn(backlog);

        // when
        doneBacklog.invoke(userId, backlogId);

        // then
        assertThat(backlog.getIsDone()).isTrue();
    }
}
