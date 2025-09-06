package com.jslog_spring.domain.todo.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.todo.entity.Todo;
import com.jslog_spring.domain.todo.exception.TodoNotFoundException;
import com.jslog_spring.domain.todo.exception.TodoOwnershipException;
import com.jslog_spring.domain.todo.repotisory.TodoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.jslog_spring.global.exception.ErrorCode.TODO_ACCESS_DENIED;
import static com.jslog_spring.global.exception.ErrorCode.TODO_NOT_FOUND;
import static fixture.MemberFixture.createMember;
import static fixture.MemberFixture.createMemberWithId;
import static fixture.TodoFixture.createTodoWithId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    @DisplayName("사용자의 모든 할 일을 조회할 수 있다.")
    public void getAllTodosTest() {
        //given
        Member author = createMember();
        Todo todo1 = createTodoWithId(1L, author, "Work", "todo title1", "todo description1");
        Todo todo2 = createTodoWithId(2L, author, "Personal", "todo title2", "todo description2");

        //when
        when(todoRepository.findByMember(author)).thenReturn(List.of(todo1, todo2));
        List<Todo> todos = todoService.getAllTodos(author);

        //then
        assertThat(todos).hasSize(2);
        assertThat(todos).containsExactlyInAnyOrder(todo1, todo2);
    }

    @Test
    @DisplayName("사용자는 특정 카테고리에 속한 모든 할 일을 조회할 수 있다.")
    public void getAllTodosByCategoryTest() {
        //given
        Member author = createMember();
        String category = "Work";
        Todo todo1 = createTodoWithId(1L, author, category, "todo title1", "todo description1");
        Todo todo2 = createTodoWithId(2L, author, category, "todo title2", "todo description2");

        //when
        when(todoRepository.findByMemberAndCategory(author, category)).thenReturn(List.of(todo1, todo2));
        List<Todo> todos = todoService.getAllTodos(author, category);

        //then
        assertThat(todos).hasSize(2);
        assertThat(todos).containsExactlyInAnyOrder(todo1, todo2);
    }

    @Test
    @DisplayName("존재하지 않는 카테고리 조회시 빈 리스트를 반환한다.")
    public void getAllTodosByCategory_EmptyListTest() {
        //given
        Member author = createMember();
        String category = "NonExistentCategory";

        //when
        when(todoRepository.findByMemberAndCategory(author, category)).thenReturn(List.of());
        List<Todo> todos = todoService.getAllTodos(author, category);

        //then
        assertThat(todos).isEmpty();
    }

    @Test
    @DisplayName("사용자는 할 일의 카테고리, 제목, 설명을 수정할 수 있다.")
    public void updateTodoTest() {
        //given
        Member author = createMember();
        Todo todo = createTodoWithId(1L, author, "Work", "todo title", "todo description");
        String newCategory = "Personal";
        String newTitle = "updated title";
        String newDescription = "updated description";

        //when
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Todo categoryUpdatedTodo = todoService.updateTodo(author, 1L, newCategory, null, null);
        Todo titleUpdatedTodo = todoService.updateTodo(author, 1L, null, newTitle, null);
        Todo descriptionIpdatedTodo = todoService.updateTodo(author, 1L, null, null, newDescription);

        //then
        assertThat(categoryUpdatedTodo.getCategory()).isEqualTo(newCategory);
        assertThat(titleUpdatedTodo.getTitle()).isEqualTo(newTitle);
        assertThat(descriptionIpdatedTodo.getDescription()).isEqualTo(newDescription);
    }

    @Test
    @DisplayName("사용자는 존재하지 않는 할 일 ID로 수정할 경우 예외가 발생한다.")
    public void updateTodo_NotFoundTest() {
        //given
        Member author = createMember();
        Long notExistentId = 999L;
        //when
        when(todoRepository.findById(notExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            todoService.updateTodo(author, notExistentId, "new cat", "new title", "new desc");
        })
                .isInstanceOf(TodoNotFoundException.class)
                .isInstanceOfSatisfying(TodoNotFoundException.class, e -> {
                            assertThat(e.getTodoId()).isEqualTo(notExistentId);
                            assertThat(e.getErrorCode()).isEqualTo(TODO_NOT_FOUND);
                        }
                );
    }

    @Test
    @DisplayName("소유자가 아닌 사용자가 할 일 수정할 경우 예외가 발생한다.")
    public void updateTodo_UnauthorizedTest() {
        //given
        Member other = createMemberWithId(1L);
        Member author = createMemberWithId(2L);
        Todo todo = createTodoWithId(1L, other, "Work", "todo title", "todo description");

        //when
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        assertThatThrownBy(() -> {
            todoService.updateTodo(author, 1L, "new cat", "new title", "new desc");
        })
                .isInstanceOf(TodoOwnershipException.class)
                .isInstanceOfSatisfying(TodoOwnershipException.class, e -> {
                            assertThat(e.getTodoId()).isEqualTo(1L);
                            assertThat(e.getMemberId()).isEqualTo(author.getId());
                            assertThat(e.getErrorCode()).isEqualTo(TODO_ACCESS_DENIED);
                        }
                );
    }

    @Test
    @DisplayName("사용자는 할 일을 완료 상태로 변경할 수 있다.")
    public void doneTodoTest() {
        //given
        Member author = createMember();
        Todo todo = createTodoWithId(1L, author, "Work", "todo title", "todo description");

        //when
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        todoService.doneTodo(author, 1L);

        //then
        assertThat(todo.isDone()).isTrue();
    }

    @Test
    @DisplayName("사용자는 존재하지 않는 할 일을 수정하려 할 경우 예외가 발생한다.")
    public void doneTodo_NotFoundTest() {
        //given
        Member author = createMember();
        Long notExistentId = 999L;

        //when
        when(todoRepository.findById(notExistentId)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> {
            todoService.doneTodo(author, notExistentId);
        }).isInstanceOf(TodoNotFoundException.class)
                .isInstanceOfSatisfying(TodoNotFoundException.class, e -> {
                    assertThat(e.getTodoId()).isEqualTo(notExistentId);
                    assertThat(e.getErrorCode()).isEqualTo(TODO_NOT_FOUND);
                });
    }

    @Test
    @DisplayName("소유자가 아닌 사용자가 할 일을 완료 상태로 변경하려 할 경우 예외가 발생한다.")
    public void doneTodo_UnauthorizedTest() {
        //given
        Member other = createMemberWithId(1L);
        Member author = createMemberWithId(2L);
        Todo todo = createTodoWithId(1L, other, "Work", "todo title", "todo description");

        //when
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        //then
        assertThatThrownBy(() -> {
            todoService.doneTodo(author, 1L);
        }).isInstanceOf(TodoOwnershipException.class)
                .isInstanceOfSatisfying(TodoOwnershipException.class, e -> {
                    assertThat(e.getTodoId()).isEqualTo(1L);
                    assertThat(e.getMemberId()).isEqualTo(author.getId());
                    assertThat(e.getErrorCode()).isEqualTo(TODO_ACCESS_DENIED);
                });
    }

    @Test
    @DisplayName("사용자는 할 일을 미완료 상태로 변경할 수 있다.")
    public void undoneTodoTest() {
        //given
        Member author = createMember();
        Todo todo = createTodoWithId(1L, author, "Work", "todo title", "todo description");
        //when
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        todoService.undoneTodo(author, 1L);

        //then
        assertThat(todo.isDone()).isFalse();
    }

    @Test
    @DisplayName("사용자는 존재하지 않는 할 일을 미완료 상태로 변경하려 할 경우 예외가 발생한다.")
    public void undoneTodo_NotFoundTest() {
        //given
        Member author = createMember();
        Long notExistentId = 999L;

        //when
        when(todoRepository.findById(notExistentId)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> {
            todoService.undoneTodo(author, notExistentId);
        }).isInstanceOf(TodoNotFoundException.class)
                .isInstanceOfSatisfying(TodoNotFoundException.class, e -> {
                    assertThat(e.getTodoId()).isEqualTo(notExistentId);
                    assertThat(e.getErrorCode()).isEqualTo(TODO_NOT_FOUND);
                });
    }

    @Test
    @DisplayName("소유자가 아닌 사용자가 할 일의 상태를 변경하려 할 경우 예외가 발생한다.")
    public void undoneTodo_UnauthorizedTest() {
        //given
        Member other = createMemberWithId(1L);
        Member author = createMemberWithId(2L);
        Todo todo = createTodoWithId(1L, other, "Work", "todo title", "todo description");

        //when
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        //then
        assertThatThrownBy(() -> {
            todoService.undoneTodo(author, 1L);
        }).isInstanceOf(TodoOwnershipException.class)
                .isInstanceOfSatisfying(TodoOwnershipException.class, e -> {
                    assertThat(e.getTodoId()).isEqualTo(1L);
                    assertThat(e.getMemberId()).isEqualTo(author.getId());
                    assertThat(e.getErrorCode()).isEqualTo(TODO_ACCESS_DENIED);
                });
    }

    @Test
    @DisplayName("사용자는 할 일의 완료 상태를 토글할 수 있다.")
    public void toggleTodoTest() {
        //given
        Member author = createMember();
        Todo todo = createTodoWithId(1L, author, "Work", "todo title", "todo description");
        assertThat(todo.isDone()).isFalse();

        //when
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));
        todoService.toggleTodo(author, 1L);

        //then
        assertThat(todo.isDone()).isTrue();

        //when
        todoService.toggleTodo(author, 1L);

        //then
        assertThat(todo.isDone()).isFalse();
    }

    @Test
    @DisplayName("사용자는 존재하지 않는 할 일을 토글하려 할 경우 예외가 발생한다.")
    public void toggleTodo_NotFoundTest() {
        //given
        Member author = createMember();
        Long notExistentId = 999L;

        //when
        when(todoRepository.findById(notExistentId)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> {
            todoService.toggleTodo(author, notExistentId);
        }).isInstanceOf(TodoNotFoundException.class)
                .isInstanceOfSatisfying(TodoNotFoundException.class, e -> {
                    assertThat(e.getTodoId()).isEqualTo(notExistentId);
                    assertThat(e.getErrorCode()).isEqualTo(TODO_NOT_FOUND);
                });
    }

    @Test
    @DisplayName("소유자가 아닌 사용자가 할 일의 상태를 토글하려 할 경우 예외가 발생한다.")
    public void toggleTodo_UnauthorizedTest() {
        //given
        Member other = createMemberWithId(1L);
        Member author = createMemberWithId(2L);
        Todo todo = createTodoWithId(1L, other, "Work", "todo title", "todo description");

        //when
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        //then
        assertThatThrownBy(() -> {
            todoService.toggleTodo(author, 1L);
        }).isInstanceOf(TodoOwnershipException.class)
                .isInstanceOfSatisfying(TodoOwnershipException.class, e -> {
                    assertThat(e.getTodoId()).isEqualTo(1L);
                    assertThat(e.getMemberId()).isEqualTo(author.getId());
                    assertThat(e.getErrorCode()).isEqualTo(TODO_ACCESS_DENIED);
                });
    }
}
