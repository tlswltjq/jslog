package com.jslog_spring.member.application;

import com.jslog_spring.member.application.dto.MemberInfo;
import com.jslog_spring.member.domain.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetMemberInfo {
    private final MemberQueryRepository repository;

    public MemberInfo invoke(Long memberId) {
        return repository.findMemberInfoById(memberId);
    }
}
