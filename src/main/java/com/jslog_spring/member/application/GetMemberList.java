package com.jslog_spring.member.application;

import com.jslog_spring.member.application.dto.MemberInfo;
import com.jslog_spring.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetMemberList {
    private final MemberRepository repository;

    public List<MemberInfo> invoke() {
        return repository.findAllMemberInfos();
    }
}
