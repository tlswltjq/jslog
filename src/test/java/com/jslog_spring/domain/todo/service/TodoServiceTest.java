package com.jslog_spring.domain.todo.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.todo.entity.Todo;
import com.jslog_spring.domain.todo.repotisory.TodoRepository;
import fixture.MemberFixture;
import fixture.TodoFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Member author = MemberFixture.createMember();
        String category = "Work";
        String title = "todo title";
        String description = "todo description";

        Todo createdTodo = TodoFixture.createTodoWithId(1L, author, category, title, description);

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
}
