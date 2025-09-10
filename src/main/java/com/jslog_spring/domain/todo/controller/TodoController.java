package com.jslog_spring.domain.todo.controller;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.todo.dto.TodoCreateRequest;
import com.jslog_spring.domain.todo.dto.TodoListResponse;
import com.jslog_spring.domain.todo.dto.TodoResponse;
import com.jslog_spring.domain.todo.dto.TodoUpdateRequest;
import com.jslog_spring.domain.todo.entity.Todo;
import com.jslog_spring.domain.todo.service.TodoService;
import com.jslog_spring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ApiResponse<TodoResponse> createTodo(@AuthenticationPrincipal Member member, @RequestBody TodoCreateRequest request) {
        Todo todo = todoService.createTodo(member, request.category(), request.title(), request.description());
        TodoResponse response = new TodoResponse(todo);
        return ApiResponse.success("201", "Todo created successfully", response);
    }

    @GetMapping
    public ApiResponse<TodoListResponse> getTodos(@AuthenticationPrincipal Member member) {
        List<Todo> allTodos = todoService.getAllTodos(member);
        TodoListResponse response = new TodoListResponse(allTodos);
        return ApiResponse.success("200", "Todos retrieved successfully", response);
    }

    @GetMapping("/{category}")
    public ApiResponse<TodoListResponse> getTodosByCategory(@AuthenticationPrincipal Member member, @PathVariable String category) {
        List<Todo> todosByCategory = todoService.getAllTodos(member, category);
        TodoListResponse response = new TodoListResponse(todosByCategory);
        return ApiResponse.success("200", "Todos retrieved successfully", response);
    }

    @GetMapping("/{id}")
    public ApiResponse<TodoResponse> getTodoById(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        Todo todo = todoService.getTodoById(member, id);
        TodoResponse response = new TodoResponse(todo);
        return ApiResponse.success("200", "Todo retrieved successfully", response);
    }

    @PutMapping
    public ApiResponse<TodoResponse> updateTodo(@AuthenticationPrincipal Member member, @RequestBody TodoUpdateRequest request) {
        Todo updatedTodo = todoService.updateTodo(member, request.id(), request.category(), request.title(), request.description());
        TodoResponse response = new TodoResponse(updatedTodo);
        return ApiResponse.success("200", "Todo updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteTodo(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        todoService.deleteTodo(member, id);
        return ApiResponse.success("204", "Todo deleted successfully");
    }

    @DeleteMapping("/{category}")
    public ApiResponse<?> deleteTodosByCategory(@AuthenticationPrincipal Member member, @PathVariable String category) {
        todoService.deleteTodosByCategory(member, category);
        return ApiResponse.success("204", "Todos deleted successfully");
    }

    @DeleteMapping
    public ApiResponse<?> deleteAllTodos(@AuthenticationPrincipal Member member) {
        todoService.deleteAllTodos(member);
        return ApiResponse.success("204", "All todos deleted successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<TodoResponse> markTodoAsDone(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        Todo todo = todoService.toggleTodo(member, id);
        TodoResponse response = new TodoResponse(todo);
        return ApiResponse.success("200", "Todo marked as done successfully", response);
    }

    @PutMapping("/{category}/done")
    public ApiResponse<TodoListResponse> markTodosByCategoryAsDone(@AuthenticationPrincipal Member member, @PathVariable String category) {
        List<Todo> todos = todoService.doneAllTodosByCategory(member, category);
        TodoListResponse response = new TodoListResponse(todos);
        return ApiResponse.success("200", "Todos in category marked as done successfully", response);
    }

    @PutMapping("/{category}/undone")
    public ApiResponse<TodoListResponse> markTodosByCategoryAsUndone(@AuthenticationPrincipal Member member, @PathVariable String category) {
        List<Todo> todos = todoService.undoneAllTodosByCategory(member, category);
        TodoListResponse response = new TodoListResponse(todos);
        return ApiResponse.success("200", "Todos in category marked as undone successfully", response);
    }
}
