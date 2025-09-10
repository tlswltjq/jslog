package com.jslog_spring.domain.todo.controller;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.todo.dto.TodoCreateRequest;
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
    public ApiResponse createTodo(@AuthenticationPrincipal Member member, @RequestBody TodoCreateRequest request) {
        Todo todo = todoService.createTodo(member, request.category(), request.title(), request.description());
        return ApiResponse.success("201", "Todo created successfully", todo);
    }

    @GetMapping
    public ApiResponse getTodos(@AuthenticationPrincipal Member member) {
        List<Todo> allTodos = todoService.getAllTodos(member);
        return ApiResponse.success("200", "Todos retrieved successfully", allTodos);
    }

    @GetMapping("/{category}")
    public ApiResponse getTodosByCategory(@AuthenticationPrincipal Member member, @PathVariable String category) {
        List<Todo> todosByCategory = todoService.getAllTodos(member, category);
        return ApiResponse.success("200", "Todos retrieved successfully", todosByCategory);
    }

    @GetMapping("/{id}")
    public ApiResponse getTodoById(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        Todo todo = todoService.getTodoById(member, id);
        return ApiResponse.success("200", "Todo retrieved successfully", todo);
    }

    @PutMapping
    public ApiResponse updateTodo(@AuthenticationPrincipal Member member, @RequestBody TodoUpdateRequest request) {
        Todo updatedTodo = todoService.updateTodo(member, request.id(), request.category(), request.title(), request.description());
        return ApiResponse.success("200", "Todo updated successfully", updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteTodo(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        todoService.deleteTodo(member, id);
        return ApiResponse.success("200", "Todo deleted successfully");
    }

    @DeleteMapping("/{category}")
    public ApiResponse deleteTodosByCategory(@AuthenticationPrincipal Member member, @PathVariable String category) {
        todoService.deleteTodosByCategory(member, category);
        return ApiResponse.success("200", "Todos deleted successfully");
    }

    @DeleteMapping
    public ApiResponse deleteAllTodos(@AuthenticationPrincipal Member member) {
        todoService.deleteAllTodos(member);
        return ApiResponse.success("200", "All todos deleted successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse markTodoAsDone(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        todoService.toggleTodo(member, id);
        return ApiResponse.success("200", "Todo marked as done successfully");
    }

    @PutMapping("/{category}/done")
    public ApiResponse markTodosByCategoryAsDone(@AuthenticationPrincipal Member member, @PathVariable String category) {
        List<Todo> todos = todoService.doneAllTodosByCategory(member, category);
        return ApiResponse.success("200", "Todos in category marked as done successfully", todos);
    }

    @PutMapping("/{category}/undone")
    public ApiResponse markTodosByCategoryAsUndone(@AuthenticationPrincipal Member member, @PathVariable String category) {
        List<Todo> todos = todoService.undoneAllTodosByCategory(member, category);
        return ApiResponse.success("200", "Todos in category marked as undone successfully", todos);
    }
}
