package com.jslog_spring.domain.todo.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.todo.entity.Todo;
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

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + id));
    }

    public List<Todo> getAllTodos(Member member){
        return todoRepository.findByMember(member);
    }

    public List<Todo> getAllTodos(Member member, String category){
        return todoRepository.findByMemberAndCategory(member, category);
    }

    public Todo updateTodo(Long id, String category, String title, String description) {
        Todo todo = getTodoById(id);
        todo.update(category, title, description);
        return todoRepository.save(todo);
    }

    public void doneTodo(Long id) {
        Todo todo = getTodoById(id);
        todo.done();
        todoRepository.save(todo);
    }

    private void doneTodo(Todo todo) {
        todo.done();
        todoRepository.save(todo);
    }

    public void undoneTodo(Long id) {
        Todo todo = getTodoById(id);
        todo.undone();
        todoRepository.save(todo);
    }

    public void toggleTodo(Long id) {
        Todo todo = getTodoById(id);
        todo.toggle();
        todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        Todo todo = getTodoById(id);
        todoRepository.delete(todo);
    }

    public void deleteTodosOfCategory(Member member, String category) {
        List<Todo> todos = todoRepository.findByMemberAndCategory(member, category);
        todoRepository.deleteAll(todos);
    }

    public void deleteAllTodos(Member member) {
        List<Todo> todos = todoRepository.findByMember(member);
        todoRepository.deleteAll(todos);
    }

    public void doneAllTodosOfCategory(Member member, String category) {
        List<Todo> todos = todoRepository.findByMemberAndCategory(member, category);
        todos.forEach(this::doneTodo);
    }
}
