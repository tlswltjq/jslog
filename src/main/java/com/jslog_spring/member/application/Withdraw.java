package com.jslog_spring.member.application;

import com.jslog_spring.member.domain.repository.MemberCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class Withdraw {
    private final MemberCommandRepository repository;

    public Long invoke(Long memberId) {
        repository.delete(memberId);
        return memberId;
    }
}
