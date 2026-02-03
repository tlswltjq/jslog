package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogQueryRepository;
import com.jslog_spring.backlog.exception.BacklogOwnerMismatchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EditBacklogTest {

    @Mock
    private BacklogQueryRepository backlogRepository;

    @InjectMocks
    private EditBacklog editBacklog;

    @Test
    @DisplayName("백로그 수정 성공")
    void invoke_success() {
        // given
        Long userId = 1L;
        Long backlogId = 1L;
        Backlog backlog = Backlog.of(userId, "원본", "설명", LocalDateTime.now());

        given(backlogRepository.findById(backlogId)).willReturn(backlog);

        // when
        editBacklog.invoke(userId, backlogId, "수정", "수정설명", LocalDateTime.now());

        // then
        verify(backlogRepository).findById(backlogId);
    }

    @Test
    @DisplayName("백로그 수정 실패 - 소유자 불일치")
    void invoke_fail_owner() {
        // given
        Long userId = 1L;
        Long otherUserId = 2L;
        Long backlogId = 1L;
        Backlog backlog = Backlog.of(userId, "원본", "설명", LocalDateTime.now());

        given(backlogRepository.findById(backlogId)).willReturn(backlog);

        // when & then
        assertThatThrownBy(() -> editBacklog.invoke(otherUserId, backlogId, "수정", "수정", LocalDateTime.now()))
                .isInstanceOf(BacklogOwnerMismatchException.class);
    }
}
