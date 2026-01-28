package com.jslog_spring.backlog.interfaces.rest;

import com.jslog_spring.auth.domain.model.AuthUserDetails;
import com.jslog_spring.backlog.application.*;
import com.jslog_spring.backlog.application.dto.BacklogInfo;
import com.jslog_spring.backlog.interfaces.rest.dto.*;
import com.jslog_spring.common.rest.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiResponse<CreateBacklogResponse> create(@AuthenticationPrincipal AuthUserDetails authUser,
                                                     @RequestBody BacklogCreateRequest request) {
        Long backlogId = addBacklog.invoke(
                authUser.getAccountId(),
                request.name(),
                request.desc(),
                request.dueDate());
        return ApiResponse.success(HttpStatus.CREATED.value(), "백로그 생성 성공", CreateBacklogResponse.from(backlogId));
    }

    @PutMapping("/{backlogId}")
    public ApiResponse<UpdateBacklogResponse> update(@AuthenticationPrincipal AuthUserDetails authUser, @PathVariable Long backlogId, @RequestBody BacklogUpdateRequest request) {
        Long id = editBacklog.invoke(
                authUser.getAccountId(),
                backlogId,
                request.name(),
                request.desc(),
                request.dueDate());
        return ApiResponse.success(HttpStatus.OK.value(), "백로그 수정 성공", UpdateBacklogResponse.from(id));
    }

    @DeleteMapping("/{backlogId}")
    public ApiResponse<DeleteBacklogResponse> delete(@AuthenticationPrincipal AuthUserDetails authUser, @PathVariable Long backlogId) {
        deleteBacklog.invoke(authUser.getAccountId(), backlogId);
        return ApiResponse.success(HttpStatus.OK.value(), "백로그 삭제 성공", DeleteBacklogResponse.from(backlogId));
    }

    @PatchMapping("/{backlogId}/done")
    public ApiResponse<DoneBacklogResponse> done(@AuthenticationPrincipal AuthUserDetails authUser, @PathVariable Long backlogId) {
        Long id = doneBacklog.invoke(authUser.getAccountId(), backlogId);
        return ApiResponse.success(HttpStatus.OK.value(), "백로그 완료 처리 성공", DoneBacklogResponse.from(id));
    }

    @PatchMapping("/{backlogId}/undone")
    public ApiResponse<UndoneBacklogResponse> undone(@AuthenticationPrincipal AuthUserDetails authUser, @PathVariable Long backlogId) {
        Long id = unDoneBacklog.invoke(authUser.getAccountId(), backlogId);
        return ApiResponse.success(HttpStatus.OK.value(), "백로그 미완료 처리 성공", UndoneBacklogResponse.from(id));
    }

    @GetMapping("/{backlogId}")
    public ApiResponse<BacklogResponse> getInfo(@AuthenticationPrincipal AuthUserDetails authUser, @PathVariable Long backlogId) {
        BacklogInfo backlog = getInfoOfBacklog.invoke(authUser.getAccountId(), backlogId);
        return ApiResponse.success(HttpStatus.OK.value(), "백로그 조회 성공", BacklogResponse.from(backlog));
    }

    @GetMapping
    public ApiResponse<List<BacklogResponse>> getAll(@AuthenticationPrincipal AuthUserDetails authUser) {
        List<BacklogInfo> backlogs = getAllMyBacklogs.invoke(authUser.getAccountId());
        return ApiResponse.success(HttpStatus.OK.value(), "내 백로그 전체 조회 성공", backlogs.stream()
                .map(BacklogResponse::from)
                .toList());
    }
}
