package com.jslog_spring.member.domain.policy;

import com.jslog_spring.member.application.dto.ProfileEditCommand;
import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.exception.ForbiddenWordException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ForbiddenWordRule implements SignUpPolicy, NicknameChangePolicy, ProfileEditPolicy {

    private static final List<String> FORBIDDEN_WORDS = List.of(
            "admin", "administrator", "관리자", "운영자");

    @Override
    public void validate(Member member) {
        checkForbiddenWord(member.getNickname());
    }

    @Override
    public void validate(String nickname) {
        checkForbiddenWord(nickname);
    }

    @Override
    public void validate(Member member, ProfileEditCommand command) {
        if (command.bio() != null) {
            checkForbiddenWord(command.bio());
        }
    }

    private void checkForbiddenWord(String content) {
        if (content == null) {
            return;
        }
        String lowerContent = content.toLowerCase();
        boolean containsForbiddenWord = FORBIDDEN_WORDS.stream()
                .anyMatch(word -> lowerContent.contains(word.toLowerCase()));

        if (containsForbiddenWord) {
            throw new ForbiddenWordException();
        }
    }
}
