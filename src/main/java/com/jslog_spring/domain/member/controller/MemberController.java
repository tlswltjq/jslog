package com.jslog_spring.domain.member.controller;

import com.jslog_spring.domain.member.dto.JoinResponse;
import com.jslog_spring.domain.member.dto.MemberInfoResponse;
import com.jslog_spring.domain.member.dto.TokenResponse;
import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.exception.UserNameDuplicationException;
import com.jslog_spring.domain.member.service.MemberService;
import com.jslog_spring.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    record JoinRequest(String username, String password, String name) {
    }

    record SignInRequest(String username, String password) {
    }

    @PostMapping("/signup")
    public ApiResponse<JoinResponse> signUp(@RequestBody JoinRequest request) {
        log.info("회원 가입 요청: username={}, name={}", request.username, request.name);
        try {
            Member member = memberService.signUp(request.username, request.password, request.name);
            JoinResponse response = new JoinResponse(member);
            return ApiResponse.success("201", "회원가입 성공", response);
        } catch (UserNameDuplicationException e) {
            return ApiResponse.failure("400", "이미 존재하는 사용자 이름입니다.");
        }
    }

    @PostMapping("/signin")
    public ApiResponse<TokenResponse> signIn(@RequestBody SignInRequest request) {
        log.info("로그인 요청: username={}", request.username);
        Pair<String, String> tokens = memberService.signIn(request.username, request.password);

        TokenResponse response = new TokenResponse(tokens.getFirst());
        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokens.getSecond())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60 * 24 * 14) // 2주
                .build();
        return ApiResponse.success("200", "로그인 성공", response, List.of(cookie));
    }

    @GetMapping("/me")
    public ApiResponse getMe(@AuthenticationPrincipal Member member) {
        MemberInfoResponse response = new MemberInfoResponse(member);
        return ApiResponse.success("200", "내 정보 조회 성공", response);
    }

    @PostMapping("/reissue")
    public ApiResponse reissue(@CookieValue("refreshToken") String refreshToken) {
        Pair<String, String> reissued = memberService.reissue(refreshToken);

        TokenResponse response = new TokenResponse(reissued.getFirst());
        ResponseCookie cookie = ResponseCookie.from("refreshToken", reissued.getSecond())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60 * 24 * 14) // 2주
                .build();
        log.info("토큰 재발급 완료");
        return ApiResponse.success("200", "토큰 재발급 성공.", response, List.of(cookie));

    }
}
