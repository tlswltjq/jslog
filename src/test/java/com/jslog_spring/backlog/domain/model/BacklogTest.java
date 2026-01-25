package com.jslog_spring.backlog.domain.model;

import com.jslog_spring.backlog.exception.BacklogOwnerMismatchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BacklogTest {

    @Test
    @DisplayName("백로그 생성 테스트")
    void createBacklog() {
        Backlog backlog = Backlog.of(1L, "할 일", "설명", LocalDateTime.now().plusDays(1));

        assertThat(backlog.getOwnedBy()).isEqualTo(1L);
        assertThat(backlog.getName()).isEqualTo("할 일");
        assertThat(backlog.getDesc()).isEqualTo("설명");
        assertThat(backlog.getIsDone()).isFalse();
    }

    @Test
    @DisplayName("백로그 소유자 확인 - 일치")
    void checkOwnerResultTrue() {
        Backlog backlog = Backlog.of(1L, "할 일", "설명", LocalDateTime.now().plusDays(1));
        backlog.checkOwner(1L);
    }

    @Test
    @DisplayName("백로그 소유자 확인 - 불일치시 예외 발생")
    void checkOwnerResultFalse() {
        Backlog backlog = Backlog.of(1L, "할 일", "설명", LocalDateTime.now().plusDays(1));

        assertThatThrownBy(() -> backlog.checkOwner(2L))
                .isInstanceOf(BacklogOwnerMismatchException.class);
    }

    @Test
    @DisplayName("백로그 완료 처리")
    void done() {
        Backlog backlog = Backlog.of(1L, "할 일", "설명", LocalDateTime.now().plusDays(1));
        backlog.done();
        assertThat(backlog.getIsDone()).isTrue();
    }

    @Test
    @DisplayName("백로그 미완료 처리")
    void undone() {
        Backlog backlog = Backlog.of(1L, "할 일", "설명", LocalDateTime.now().plusDays(1));
        backlog.done();
        backlog.undone();
        assertThat(backlog.getIsDone()).isFalse();
    }

    @Test
    @DisplayName("백로그 수정")
    void update() {
        Backlog backlog = Backlog.of(1L, "할 일", "설명", LocalDateTime.now().plusDays(1));
        String newName = "수정된 할 일";
        String newDesc = "수정된 설명";
        LocalDateTime newDueDate = LocalDateTime.now().plusDays(2);

        backlog.update(newName, newDesc, newDueDate);

        assertThat(backlog.getName()).isEqualTo(newName);
        assertThat(backlog.getDesc()).isEqualTo(newDesc);
        assertThat(backlog.getDueDate()).isEqualTo(newDueDate);
    }
}
