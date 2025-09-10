package com.jslog_spring.domain.todo.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.todo.entity.Todo;
import com.jslog_spring.domain.todo.exception.TodoNotFoundException;
import com.jslog_spring.domain.todo.exception.TodoOwnershipException;
import com.jslog_spring.domain.todo.repotisory.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public Todo createTodo(Member member, String category, String title, String description) {
        Todo todo = Todo.create(member, category, title, description);
        return todoRepository.save(todo);
    }

    public Todo getTodoById(Member member, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        if (!todo.getMember().equals(member)) {
            throw new TodoOwnershipException(id, member.getId());
        } else {
            return todo;
        }
    }

    public List<Todo> getAllTodos(Member member) {
        return todoRepository.findByMember(member);
    }

    public List<Todo> getAllTodos(Member member, String category) {
        return todoRepository.findByMemberAndCategory(member, category);
    }

    public Todo updateTodo(Member member, Long id, String category, String title, String description) {
        Todo todo = getTodoById(member, id);
        todo.update(category, title, description);
        return todoRepository.save(todo);
    }

    public Todo doneTodo(Member member, Long id) {
        Todo todo = getTodoById(member, id);
        todo.done();
        return todoRepository.save(todo);
    }

    private void doneTodo(Todo todo) {
        todo.done();
        todoRepository.save(todo);
    }

    public Todo undoneTodo(Member member, Long id) {
        Todo todo = getTodoById(member, id);
        todo.undone();
        return todoRepository.save(todo);
    }

    private void undoneTodo(Todo todo) {
        todo.undone();
        todoRepository.save(todo);
    }

    public Todo toggleTodo(Member member, Long id) {
        Todo todo = getTodoById(member, id);
        todo.toggle();
        return todoRepository.save(todo);
    }

    public void deleteTodo(Member member, Long id) {
        Todo todo = getTodoById(member, id);
        todoRepository.delete(todo);
    }

    public void deleteTodosByCategory(Member member, String category) {
        List<Todo> todos = todoRepository.findByMemberAndCategory(member, category);
        todoRepository.deleteAll(todos);
    }

    public void deleteAllTodos(Member member) {
        List<Todo> todos = todoRepository.findByMember(member);
        todoRepository.deleteAll(todos);
    }

    public List<Todo> doneAllTodosByCategory(Member member, String category) {
        List<Todo> todos = todoRepository.findByMemberAndCategory(member, category);
        todos.forEach(this::doneTodo);
        return todos;
    }

    public List<Todo> undoneAllTodosByCategory(Member member, String category) {
        List<Todo> todos = todoRepository.findByMemberAndCategory(member, category);
        todos.forEach(this::undoneTodo);
        return todos;
    }
}
