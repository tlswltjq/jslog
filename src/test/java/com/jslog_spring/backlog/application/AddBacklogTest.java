package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.domain.model.Backlog;
import com.jslog_spring.backlog.domain.repository.BacklogCommandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddBacklogTest {

    @Mock
    private BacklogCommandRepository backlogRepository;

    @InjectMocks
    private AddBacklog addBacklog;

    @Test
    @DisplayName("백로그 추가 서비스 테스트")
    void invoke() {
        // given
        Long userId = 1L;
        String name = "할 일";
        String desc = "설명";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        Backlog backlog = Backlog.of(userId, name, desc, dueDate);

        when(backlogRepository.save(any(Backlog.class))).thenReturn(backlog);

        // when
        addBacklog.invoke(userId, name, desc, dueDate);

        // then
        verify(backlogRepository).save(any(Backlog.class));
    }
}
