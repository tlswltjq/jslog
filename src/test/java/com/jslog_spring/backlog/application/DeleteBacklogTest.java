package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogCommandRepository;
import com.jslog_spring.backlog.domain.repository.BacklogQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteBacklogTest {

    @Mock
    private BacklogCommandRepository commandRepository;
    @Mock
    private BacklogQueryRepository queryRepository;

    private DeleteBacklog deleteBacklog;

    @BeforeEach
    void setUp() {
        deleteBacklog = new DeleteBacklog(commandRepository, queryRepository);
    }

    @Test
    @DisplayName("백로그 삭제 성공")
    void invoke() {
        // given
        Long userId = 1L;
        Long backlogId = 1L;
        Backlog backlog = Backlog.of(userId, "할 일", "설명", LocalDateTime.now());

        given(queryRepository.findById(backlogId)).willReturn(backlog);

        // when
        deleteBacklog.invoke(userId, backlogId);

        // then
        verify(commandRepository).delete(backlogId);
    }
}
