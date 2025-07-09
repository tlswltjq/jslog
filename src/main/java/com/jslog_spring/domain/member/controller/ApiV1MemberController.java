package com.jslog_spring.domain.member.controller;

import com.jslog_spring.domain.member.dto.MemberDTO;
import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.service.MemberService;
import com.jslog_spring.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
    private final MemberService memberService;

    record MemberJoinRequest(
            @NotBlank
            @Size(min = 2, max = 30)
            String username,
            @NotBlank
            @Size(min = 2, max = 30)
            String password,
            @NotBlank
            @Size(min = 2, max = 30)
            String nickname
    ) {
    }

    @PostMapping
    public RsData<MemberDTO> join(@Valid @RequestBody MemberJoinRequest request) {
        Member member = memberService.join(
                request.username,
                request.password,
                request.nickname
        );
        return new RsData<>("201-1", "%s님 환영합니다.".formatted(request.username), new MemberDTO(member));
    }
}
