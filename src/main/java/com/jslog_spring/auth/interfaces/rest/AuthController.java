package com.jslog_spring.auth.interfaces.rest;

import com.jslog_spring.auth.application.dto.LoginRequest;
import com.jslog_spring.auth.application.dto.TokenResponse;
import com.jslog_spring.auth.application.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그아웃", description = "Refresh Token을 사용하여 로그아웃합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Valid @RequestBody com.jslog_spring.auth.application.dto.TokenReissueRequest request) {
        authService.logout(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "토큰 재발급", description = "Refresh Token을 사용하여 새로운 Access Token과 Refresh Token을 발급받습니다.")
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(
            @Valid @RequestBody com.jslog_spring.auth.application.dto.TokenReissueRequest request) {
        TokenResponse response = authService.reissue(request);
        return ResponseEntity.ok(response);
    }
}
