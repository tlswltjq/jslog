package com.jslog_spring.member.domain.policy;

import com.jslog_spring.member.application.dto.ProfileEditCommand;
import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.exception.BioLengthExceededException;
import org.springframework.stereotype.Component;

@Component
public class BioRule implements ProfileEditPolicy {
    private static final int MAX_BIO_LENGTH = 200;

    @Override
    public void validate(Member member, ProfileEditCommand command) {
        String bio = command.bio();
        if (bio == null) {
            return;
        }
        if (bio.length() > MAX_BIO_LENGTH) {
            throw new BioLengthExceededException();
        }
    }
}
