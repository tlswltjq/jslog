package com.jslog_spring.backlog.interfaces.rest;

import com.jslog_spring.backlog.application.dto.BacklogInfo;
import java.util.List;

import com.jslog_spring.backlog.application.*;
import com.jslog_spring.backlog.interfaces.rest.dto.BacklogCreateRequest;
import com.jslog_spring.backlog.interfaces.rest.dto.BacklogUpdateRequest;
import com.jslog_spring.common.rest.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/backlogs")
public class BacklogController {
    private final AddBacklog addBacklog;
    private final EditBacklog editBacklog;
    private final DeleteBacklog deleteBacklog;
    private final DoneBacklog doneBacklog;
    private final UnDoneBacklog unDoneBacklog;
    private final GetInfoOfBacklog getInfoOfBacklog;
    private final GetAllMyBacklogs getAllMyBacklogs;

    @PostMapping
    public ApiResponse<Long> create(@RequestBody BacklogCreateRequest request) {
        Long backlogId = addBacklog.invoke(
                1L,
                request.name(),
                request.desc(),
                request.dueDate());
        return ApiResponse.success(HttpStatus.CREATED.value(), "백로그 생성 성공", backlogId);
    }

    @PutMapping("/{backlogId}")
    public ApiResponse<Void> update(@PathVariable Long backlogId, @RequestBody BacklogUpdateRequest request) {
        editBacklog.invoke(
                1L,
                backlogId,
                request.name(),
                request.desc(),
                request.dueDate());
        return ApiResponse.success(HttpStatus.OK.value(), "백로그 수정 성공", null);
    }

    @DeleteMapping("/{backlogId}")
    public ApiResponse<Void> delete(@PathVariable Long backlogId) {
        deleteBacklog.invoke(1L, backlogId);
        return ApiResponse.success(HttpStatus.OK.value(), "백로그 삭제 성공", null);
    }

    @PatchMapping("/{backlogId}/done")
    public ApiResponse<Long> done(@PathVariable Long backlogId) {
        Long id = doneBacklog.invoke(1L, backlogId);
        return ApiResponse.success(HttpStatus.OK.value(), "백로그 완료 처리 성공", id);
    }

    @PatchMapping("/{backlogId}/undone")
    public ApiResponse<Long> undone(@PathVariable Long backlogId) {
        Long id = unDoneBacklog.invoke(1L, backlogId);
        return ApiResponse.success(HttpStatus.OK.value(), "백로그 미완료 처리 성공", id);
    }

    @GetMapping("/{backlogId}")
    public ApiResponse<BacklogInfo> getInfo(@PathVariable Long backlogId) {
        BacklogInfo backlog = getInfoOfBacklog.invoke(1L, backlogId);
        return ApiResponse.success(HttpStatus.OK.value(), "백로그 조회 성공", backlog);
    }

    @GetMapping
    public ApiResponse<List<BacklogInfo>> getAll() {
        List<BacklogInfo> backlogs = getAllMyBacklogs.invoke(1L);
        return ApiResponse.success(HttpStatus.OK.value(), "내 백로그 전체 조회 성공", backlogs);
    }
}
