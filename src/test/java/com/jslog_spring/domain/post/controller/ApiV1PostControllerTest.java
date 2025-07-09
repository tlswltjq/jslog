package com.jslog_spring.domain.post.controller;

import com.jslog_spring.domain.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApiV1PostControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("게시글 생성")
    void createPost() {
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void getPosts() {
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void getPost() {
    }

    @Test
    @DisplayName("게시글 수정")
    void updatePost() {
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() {
    }
//todo : 테스트 작성하기
}