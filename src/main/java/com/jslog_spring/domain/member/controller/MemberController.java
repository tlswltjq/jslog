package com.jslog_spring.domain.member.controller;

import com.jslog_spring.domain.member.dto.JoinResponse;
import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.exception.UserNameDuplicationException;
import com.jslog_spring.domain.member.service.MemberService;
import com.jslog_spring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    record JoinRequest(String username, String password, String name) {
    }

    record SignInRequest(String username, String password) {
    }

    record SignInResponse(String accessToken) {
    }

    @PostMapping("/signup")
    public ApiResponse<JoinResponse> signUp(@RequestBody JoinRequest request) {
        log.info("회원 가입 요청: username={}, name={}", request.username, request.name);
        try {
            Member member = memberService.join(request.username, request.password, request.name);
            JoinResponse response = new JoinResponse(member);
            return ApiResponse.success("201", "회원가입 성공", response);
        } catch (UserNameDuplicationException e) {
            return ApiResponse.failure("400", "이미 존재하는 사용자 이름입니다.");
        }
    }

    @PostMapping("/signin")
    public ApiResponse<SignInResponse> signIn(@RequestBody SignInRequest request) {
        log.info("로그인 요청: username={}", request.username);
        String accessToken = memberService.signIn(request.username, request.password);
        SignInResponse response = new SignInResponse(accessToken);
        return ApiResponse.success("200", "로그인 성공", response);
    }

    @GetMapping("/me")
    public ApiResponse getMe(@AuthenticationPrincipal Member member) {
        return ApiResponse.success("200", member.getName()+ "의 마이페이지.");
    }

}
