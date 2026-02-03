package com.jslog_spring.member.infrastructure;

import com.jslog_spring.member.application.dto.MemberInfo;
import com.jslog_spring.member.domain.model.Member;
import com.jslog_spring.member.domain.repository.MemberCommandRepository;
import com.jslog_spring.member.domain.repository.MemberQueryRepository;
import com.jslog_spring.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryAdapter implements MemberCommandRepository, MemberQueryRepository {
    private final MemberJpaRepository repository;

    @Override
    public Member save(Member member) {
        return repository.save(member);
    }

    @Override
    public Member findById(Long id) {
        return repository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public MemberInfo findMemberInfoById(Long id) {
        return repository.findMemberInfoById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public List<MemberInfo> findAllMemberInfos() {
        return repository.findAllMemberInfos();
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return repository.existsByNickname(nickname);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
