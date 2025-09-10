package com.jslog_spring.domain.todo.dto;

import com.jslog_spring.domain.todo.entity.Todo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public record TodoListResponse(
        Map<String, List<TodoResponse>> allTodosByCategory
) {
    public TodoListResponse(List<Todo> todos) {
        this(todos.stream()
            .collect(Collectors.groupingBy(
                Todo::getCategory,
                Collectors.mapping(TodoResponse::new, toList())
                )
            )
        );
    }
}
