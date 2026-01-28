package com.jslog_spring.member.domain.policy;

import com.jslog_spring.member.application.dto.ProfileEditCommand;
import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.repository.MemberRepository;
import com.jslog_spring.member.exception.NicknameDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NicknameRule implements SignUpPolicy, NicknameChangePolicy, ProfileEditPolicy {
    private final MemberRepository memberRepository;

    @Override
    public void validate(Member member) {
        checkDuplication(member.getNickname());
    }

    @Override
    public void validate(String nickname) {
        checkDuplication(nickname);
    }

    @Override
    public void validate(Member member, ProfileEditCommand command) {
        if (member.getNickname().equals(command.nickname())) {
            return;
        }
        checkDuplication(command.nickname());
    }

    private void checkDuplication(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new NicknameDuplicationException();
        }
    }
}
