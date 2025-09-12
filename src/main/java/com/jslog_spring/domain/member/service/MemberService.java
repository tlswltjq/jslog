package com.jslog_spring.domain.member.service;

import com.jslog_spring.domain.member.entity.Member;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;

public interface MemberService {
    Member signUp(String username, String password, String name);
    Member getMember(String username);
    Member updateUserName(Member member, String newName);
    Pair<String, String> signIn(String username, String password);
    Pair<String, String> reissue(String refreshToken);
    LocalDateTime signOut(String username);
}
