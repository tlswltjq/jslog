package com.jslog_spring.member.application;

import com.jslog_spring.member.application.dto.ProfileEditCommand;
import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.policy.ProfileEditPolicy;
import java.util.List;
import com.jslog_spring.member.domain.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EditProfile {
    private final List<ProfileEditPolicy> policies;
    private final MemberQueryRepository repository;

    // 프로필 수정(현재는 bio만, Member의 변동으로 프로필에 담을 내용이 많아지면 파라미터 및 구현 수정 필요)
    public Long invoke(Long memberId, String bio) {
        Member member = repository.findById(memberId);

        ProfileEditCommand command = new ProfileEditCommand(bio);

        policies.forEach(policy -> policy.validate(member, command));

        member.changeBio(bio);
        return memberId;
    }
}
