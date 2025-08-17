package com.jslog_spring.domain.member.controller;

import com.jslog_spring.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> signUp(@RequestBody JoinRequest request) {
        log.info("회원 가입 요청: username={}, name={}", request.username, request.name);
        memberService.join(request.username, request.password, request.name);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request) {
        log.info("로그인 요청: username={}", request.username);
        String accessToken = memberService.signIn(request.username, request.password);
        SignInResponse response = new SignInResponse(accessToken);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMe() {
        return ResponseEntity.ok("마이페이지는 아직 구현되지 않았습니다.");
    }

}
