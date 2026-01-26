package com.jslog_spring.member.interfaces.rest;

import com.jslog_spring.common.rest.ApiResponse;
import com.jslog_spring.member.application.*;
import com.jslog_spring.member.interfaces.rest.dto.*;
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
    public ApiResponse<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        Long memberId = signUp.invoke(request.getNickname());
        return ApiResponse.success(HttpStatus.CREATED.value(), "회원가입 성공",
                SignUpResponse.of(memberId, request.getNickname()));
    }

    @DeleteMapping("/{memberId}")
    public ApiResponse<WithdrawResponse> withdraw(@PathVariable Long memberId) {
        withdraw.invoke(memberId);
        return ApiResponse.success(HttpStatus.OK.value(), "회원 탈퇴 성공", WithdrawResponse.from(memberId));
    }

    @GetMapping("/{memberId}")
    public ApiResponse<MemberResponse> getMemberInfo(@PathVariable Long memberId) {
        return ApiResponse.success(HttpStatus.OK.value(), "회원 조회 성공",
                MemberResponse.from(getMemberInfo.invoke(memberId)));
    }

    @GetMapping
    public ApiResponse<List<MemberResponse>> getMemberList() {
        return ApiResponse.success(HttpStatus.OK.value(), "회원 목록 조회 성공", getMemberList.invoke().stream()
                .map(MemberResponse::from)
                .toList());
    }

    @PatchMapping("/{memberId}/nickname")
    public ApiResponse<ChangeNicknameResponse> changeNickname(@PathVariable Long memberId, @RequestBody @Valid ChangeNicknameRequest request) {
        changeNickname.invoke(memberId, request.getNickname());
        return ApiResponse.success(HttpStatus.OK.value(), "닉네임 변경 성공",
                ChangeNicknameResponse.of(memberId, request.getNickname()));
    }

    @PatchMapping("/{memberId}/profile")
    public ApiResponse<EditProfileResponse> editProfile(@PathVariable Long memberId, @RequestBody EditProfileRequest request) {
        editProfile.invoke(memberId, request.getBio());
        return ApiResponse.success(HttpStatus.OK.value(), "프로필 수정 성공",
                EditProfileResponse.of(memberId, request.getBio()));
    }
}
