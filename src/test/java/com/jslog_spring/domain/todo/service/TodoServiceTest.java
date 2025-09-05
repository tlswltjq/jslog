package com.jslog_spring.domain.todo.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.todo.entity.Todo;
import com.jslog_spring.domain.todo.exception.TodoNotFoundException;
import com.jslog_spring.domain.todo.exception.TodoOwnershipException;
import com.jslog_spring.domain.todo.repotisory.TodoRepository;
import fixture.MemberFixture;
import fixture.TodoFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static fixture.MemberFixture.*;
import static fixture.TodoFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private TodoService todoService;

    @Test
    @DisplayName("사용자가 카테고리, 할 일 제목, 할 일 설명을 입력하여 새로운 할 일을 생성할 수 있다.")
    public void createTodoTest() {
        //given
        Member author = createMember();
        String category = "Work";
        String title = "todo title";
        String description = "todo description";

        Todo createdTodo = createTodoWithId(1L, author, category, title, description);

        //when
        when(todoRepository.save(any(Todo.class))).thenReturn(createdTodo);
        Todo todo = todoService.createTodo(author, category, title, description);

        //then
        assertThat(todo.getId()).isNotNull();
        assertThat(todo.getMember()).isEqualTo(author);
        assertThat(todo.getCategory()).isEqualTo(category);
        assertThat(todo.getTitle()).isEqualTo(title);
        assertThat(todo.getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("사용자는 할 일의 ID로 특정 할 일을 조회할 수 있다.")
    public void getTodoByIdTest() {
        //given
        Member author = createMember();
        Todo todo = createTodoWithId(1L, author, "Work", "todo title", "todo description");

        //when
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        Todo foundTodo = todoService.getTodoById(author, 1L);

        //then
        assertThat(foundTodo).isEqualTo(todo);
    }

    @Test
    @DisplayName("사용자는 존재하지 않는 할 일 ID로 조회할 경우 예외가 발생한다.")
    public void getTodoById_NotFoundTest() {
        //given
        Member author = createMember();

        //when
        when(todoRepository.findById(999L)).thenReturn(Optional.empty());
        try {
            todoService.getTodoById(author, 999L);
        } catch (TodoNotFoundException e) {
            //then
            assertThat(e.getMessage()).isEqualTo("Todo not found");
            assertThat(e.getTodoId()).isEqualTo(999L);
        }
    }

    @Test
    @DisplayName("사용자는 권한 없는 할 일 ID로 조회할 경우 예외가 발생한다.")
    public void getTodoById_UnauthorizedTest() {
        //given
        Member author = createMemberWithId(1L);
        Member other = createMemberWithId(2L);
        Todo todo = createTodoWithId(1L, other, "Work", "todo title", "todo description");

        //when
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        try {
            todoService.getTodoById(author, 1L);
        } catch (TodoOwnershipException e) {
            //then
            assertThat(e.getMessage()).isEqualTo("Access to the requested Todo is denied");
        }
    }
}
