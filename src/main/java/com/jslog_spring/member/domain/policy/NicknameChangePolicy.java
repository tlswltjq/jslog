package com.jslog_spring.member.domain.policy;

public interface NicknameChangePolicy {
    void validate(String nickname);
}
