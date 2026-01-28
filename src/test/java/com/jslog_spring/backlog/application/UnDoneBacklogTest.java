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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnDoneBacklogTest {

    @Mock
    private BacklogRepository backlogRepository;

    @InjectMocks
    private UnDoneBacklog unDoneBacklog;

    @Test
    @DisplayName("백로그 미완료 처리 테스트")
    void undone() {
        // given
        Long userId = 1L;
        Long backlogId = 10L;
        Backlog backlog = Backlog.of(userId, "name", "desc", LocalDateTime.now());
        backlog.done(); // make it done first

        when(backlogRepository.findById(backlogId)).thenReturn(backlog);

        // when
        unDoneBacklog.invoke(userId, backlogId);

        // then
        assertThat(backlog.getIsDone()).isFalse();
        verify(backlogRepository).findById(backlogId);
    }
}
