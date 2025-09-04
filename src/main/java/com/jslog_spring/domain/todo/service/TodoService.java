package com.jslog_spring.domain.todo.service;

import com.jslog_spring.domain.todo.repotisory.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

}
