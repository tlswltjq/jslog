package fixture;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.todo.entity.Todo;
import org.springframework.test.util.ReflectionTestUtils;

public class TodoFixture {
    public static Todo createTodo(Member member, String category, String title, String description) {
        return Todo.create(member, category, title, description);
    }

    public static Todo createTodo(String category, String title, String description) {
        Member member = MemberFixture.createMember();
        return createTodo(member, category, title, description);
    }

    public static Todo createTodo() {
        Member member = MemberFixture.createMember();
        String category = "DEFAULT_CATEGORY";
        String title = "DEFAULT_TITLE";
        String description = "DEFAULT_DESCRIPTION";
        return createTodo(member, category, title, description);
    }

    public static Todo createTodoWithId(Long id, Member member, String category, String title, String description) {
        Todo todo = Todo.create(member, category, title, description);
        ReflectionTestUtils.setField(todo, "id", id);
        return todo;
    }

    public static Todo createUndoneTodo() {
        return createTodo();
    }

    public static Todo createDoneTodo() {
        Todo todo = createTodo();
        todo.done(); // todo를 '완료' 상태로 변경
        return todo;
    }

    public static Todo createDoneTodoWithId(Long id, Member member) {
        Todo todo = createTodoWithId(id, member, "DEFAULT_CATEGORY", "DEFAULT_TITLE", "DEFAULT_DESCRIPTION");
        todo.done(); // todo를 '완료' 상태로 변경
        return todo;
    }
}