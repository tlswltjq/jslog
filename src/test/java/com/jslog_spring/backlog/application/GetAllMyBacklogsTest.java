package com.jslog_spring.backlog.application;

import com.jslog_spring.backlog.application.dto.BacklogInfo;
import com.jslog_spring.backlog.domain.repository.BacklogRepository;
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
class GetAllMyBacklogsTest {

    @Mock
    private BacklogRepository backlogRepository;

    @InjectMocks
    private GetAllMyBacklogs getAllMyBacklogs;

    @Test
    @DisplayName("내 백로그 전체 조회 테스트")
    void getAllMyBacklogs() {
        // given
        Long userId = 1L;
        BacklogInfo info1 = new BacklogInfo(1L, userId, "name1", "desc1", LocalDateTime.now(), false,
                LocalDateTime.now(), LocalDateTime.now());
        BacklogInfo info2 = new BacklogInfo(2L, userId, "name2", "desc2", LocalDateTime.now(), true,
                LocalDateTime.now(), LocalDateTime.now());

        when(backlogRepository.findAllInfoByOwnedBy(userId)).thenReturn(List.of(info1, info2));

        // when
        List<BacklogInfo> result = getAllMyBacklogs.invoke(userId);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(info1, info2);
    }
}
