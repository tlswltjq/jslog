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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteBacklogTest {

    @Mock
    private BacklogRepository backlogRepository;

    @InjectMocks
    private DeleteBacklog deleteBacklog;

    @Test
    @DisplayName("백로그 삭제 성공")
    void invoke() {
        // given
        Long userId = 1L;
        Long backlogId = 1L;
        Backlog backlog = Backlog.of(userId, "할 일", "설명", LocalDateTime.now());

        given(backlogRepository.findById(backlogId)).willReturn(backlog);

        // when
        deleteBacklog.invoke(userId, backlogId);

        // then
        verify(backlogRepository).delete(backlogId);
    }
}
