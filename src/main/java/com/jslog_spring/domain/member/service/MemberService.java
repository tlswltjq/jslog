package com.jslog_spring.domain.member.service;

import com.jslog_spring.domain.member.entity.Member;

public interface MemberService {
    Member join(String username, String password, String name);
    Member getMember(String username);
    Member updateUserName(Member member, String newName);
}
