package com.jslog_spring.member.interfaces.rest;

import com.jslog_spring.common.rest.ApiResponse;
import com.jslog_spring.member.application.*;
import com.jslog_spring.member.application.dto.MemberInfo;
import com.jslog_spring.member.interfaces.rest.dto.ChangeNicknameRequest;
import com.jslog_spring.member.interfaces.rest.dto.EditProfileRequest;
import com.jslog_spring.member.interfaces.rest.dto.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final SignUp signUp;
    private final Withdraw withdraw;
    private final GetMemberInfo getMemberInfo;
    private final GetMemberList getMemberList;
    private final ChangeNickname changeNickname;
    private final EditProfile editProfile;

    @PostMapping
    public ApiResponse<Void> signUp(@RequestBody @Valid SignUpRequest request) {
        signUp.invoke(request.getNickname());
        return ApiResponse.success(HttpStatus.CREATED.value(), "회원가입 성공", null);
    }

    @DeleteMapping("/{memberId}")
    public ApiResponse<Void> withdraw(@PathVariable Long memberId) {
        withdraw.invoke(memberId);
        return ApiResponse.success(HttpStatus.OK.value(), "회원 탈퇴 성공", null);
    }

    @GetMapping("/{memberId}")
    public ApiResponse<MemberInfo> getMemberInfo(@PathVariable Long memberId) {
        return ApiResponse.success(HttpStatus.OK.value(), "회원 조회 성공", getMemberInfo.invoke(memberId));
    }

    @GetMapping
    public ApiResponse<List<MemberInfo>> getMemberList() {
        return ApiResponse.success(HttpStatus.OK.value(), "회원 목록 조회 성공", getMemberList.invoke());
    }

    @PatchMapping("/{memberId}/nickname")
    public ApiResponse<Void> changeNickname(@PathVariable Long memberId,
            @RequestBody @Valid ChangeNicknameRequest request) {
        changeNickname.invoke(memberId, request.getNickname());
        return ApiResponse.success(HttpStatus.OK.value(), "닉네임 변경 성공", null);
    }

    @PatchMapping("/{memberId}/profile")
    public ApiResponse<Void> editProfile(@PathVariable Long memberId, @RequestBody EditProfileRequest request) {
        editProfile.invoke(memberId, request.getBio());
        return ApiResponse.success(HttpStatus.OK.value(), "프로필 수정 성공", null);
    }
}
