package com.jslog_spring.member.domain.policy;

import com.jslog_spring.member.application.dto.ProfileEditCommand;
import com.jslog_spring.member.domain.model.Member;

public interface ProfileEditPolicy {
    void validate(Member member, ProfileEditCommand command);
}
