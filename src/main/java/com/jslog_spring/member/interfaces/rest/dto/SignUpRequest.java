package com.jslog_spring.member.interfaces.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수입니다.")
    @jakarta.validation.constraints.Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @jakarta.validation.constraints.Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;
}
