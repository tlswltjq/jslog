package com.jslog_spring.domain.member.controller;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApiV1MemberControllerTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("회원가입")
    // POST /api/v1/members
    void joinTest() throws Exception {
        String username = "testuser";
        String password = "testpassword";
        String nickname = "testnickname";

        ResultActions result = mvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                {
                                  "username": "%s",
                                  "password": "%s",
                                  "nickname": "%s"
                                }
                                """, username, password, nickname).stripIndent())
                )
                .andDo(print());
        Member member = memberService.findByUsername(username).get();

        result
                .andExpect(handler().handlerType(ApiV1MemberController.class))
                .andExpect(handler().methodName("join"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultCode").value("201-1"))
                .andExpect(jsonPath("$.msg").value("%s님 환영합니다.".formatted(member.getUsername())))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.id").value(member.getId()))
                .andExpect(jsonPath("$.data.createdDate").value(member.getCreateDate().toString()))
                .andExpect(jsonPath("$.data.modifiedDate").value(member.getModifyDate().toString()))
                .andExpect(jsonPath("$.data.nickname").value(member.getNickname()));
    }

    @Test
    @DisplayName("내 정보 조회")
    // GET /api/v1/members/me
    void findMySelfTest() {
        fail();
    }

    @Test
    @DisplayName("회원 단건 조회")
    // GET /api/v1/members/{id}
    void findMemberTest() {
        fail();
    }

    @Test
    @DisplayName("회원 다건 조회")
    // GET /api/v1/members
    void findMembersTest() {
        fail();
    }

    @Test
    @DisplayName("로그인")
    // POST /api/v1/members/login
    void loginTest() {
        fail();
    }

    @Test
    @DisplayName("로그아웃")
    // POST /api/v1/members/logout
    void logoutTest() {
        fail();
    }

    @Test
    @DisplayName("회원 정보 수정")
    // PATCH /api/v1/members/me
    void updateMemberInfo() {
        fail();
    }

    @Test
    @DisplayName("회원 탈퇴")
    // DELETE /api/v1/members/me
    void deleteMemberTest() {
        fail();
    }

}