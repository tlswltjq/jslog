package com.jslog_spring.domain.member.controller;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/signup")
    public ResponseEntity<?> join(@RequestBody JoinRequest request) {
        log.info("회원 가입 요청: username={}, name={}", request.username, request.name);
        try {
            Member joinedMember = memberService.join(request.username, request.password, request.name);
            return ResponseEntity.status(201).body("회원 가입 성공: " + joinedMember.getUsername());
        } catch (IllegalArgumentException e) {
            log.error("회원 가입 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("회원 가입 실패: " + e.getMessage());
        } catch (Exception e) {
            log.error("예기치 못한 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("회원 가입 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(){

        return ResponseEntity.ok("로그인 기능은 아직 구현되지 않았습니다.");
    }

    @GetMapping("/me")
    public ResponseEntity<?> signUp() {
        return ResponseEntity.ok("마이페이지는 아직 구현되지 않았습니다.");
    }

}
