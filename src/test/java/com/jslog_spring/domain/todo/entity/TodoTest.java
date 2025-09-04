package com.jslog_spring.domain.todo.entity;

import com.jslog_spring.domain.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static fixture.MemberFixture.createMember;
import static fixture.TodoFixture.createTodo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TodoTest {

    @Test
    @DisplayName("Todo 생성 테스트")
    void todoCreationTest() {
        // Given
        Long id = 1L;
        Member member = createMember();
        String category = "Work";
        String title = "Complete report";
        String description = "Finish the quarterly report by end of day";

        // When
        Todo todo = createTodo(member, category, title, description);
        // Then
        assertNotNull(todo);
        Assertions.assertThat(todo.getMember()).isEqualTo(member);
        Assertions.assertThat(todo.getCategory()).isEqualTo(category);
        Assertions.assertThat(todo.getTitle()).isEqualTo(title);
        Assertions.assertThat(todo.getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("Todo카테고리 업데이트 테스트")
    void todoCategoryUpdateTest() {
        // Given
        String newCategory = "Personal";
        Todo todo = createTodo();

        // When
        todo.update(newCategory, null, null);

        // Then
        Assertions.assertThat(todo.getCategory()).isEqualTo(newCategory);
    }

    @Test
    @DisplayName("Todo제목 업데이트 테스트")
    void todoTitleUpdateTest() {
        // Given
        String newTitle = "newTitle";
        Todo todo = createTodo();

        // When
        todo.update(null, newTitle, null);

        // Then
        Assertions.assertThat(todo.getTitle()).isEqualTo(newTitle);
    }

    @Test
    @DisplayName("Todo설명 업데이트 테스트")
    void todoDescriptionUpdateTest() {
        // Given
        String description = "newDescription";
        Todo todo = createTodo();

        // When
        todo.update(null, null, description);

        // Then
        Assertions.assertThat(todo.getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("Todo 완료 테스트")
    void todoDoneTest() {
        // Given
        Todo todo = createTodo();

        // When
        todo.done();

        // Then
        Assertions.assertThat(todo.isCompleted()).isTrue();
    }

    @Test
    @DisplayName("Todo 미완료 테스트")
    void todoUndoneTest() {
        // Given
        Todo todo = createTodo();
        todo.done(); // 먼저 완료 상태로 변경

        // When
        todo.undone();

        // Then
        Assertions.assertThat(todo.isCompleted()).isFalse();
    }

    @Test
    @DisplayName("Todo토글 테스트")
    void todoToggleCompletedTest() {
        // Given
        Todo todo = createTodo();
        boolean initialStatus = todo.isCompleted();

        // When
        todo.toggle();

        // Then
        Assertions.assertThat(todo.isCompleted()).isEqualTo(!initialStatus);
    }
}