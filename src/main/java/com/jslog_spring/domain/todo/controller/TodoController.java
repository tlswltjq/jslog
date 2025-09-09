package com.jslog_spring.domain.todo.controller;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.todo.dto.TodoCreateRequest;
import com.jslog_spring.domain.todo.entity.Todo;
import com.jslog_spring.domain.todo.service.TodoService;
import com.jslog_spring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
