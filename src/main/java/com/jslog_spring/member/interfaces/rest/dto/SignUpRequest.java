package com.jslog_spring.member.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;
}
