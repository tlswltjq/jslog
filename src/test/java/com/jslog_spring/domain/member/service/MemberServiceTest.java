package com.jslog_spring.domain.member.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.repository.MemberRepository;
import com.jslog_spring.global.security.JwtUtil;
import fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private StringRedisTemplate redisTemplate;
    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    @DisplayName("username(아이디), password, name을 입력받아 회원가입에 성공하고 Member 객체를 반환한다.")
    void joinTest() {
        //given
        String username = "test@example.com";
        String password = "password";
        String name = "testUser";
        Member exceptedMember = MemberFixture.createMember(username, "encodedPassword", name);

        //when
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(memberRepository.existsByUsername(any(String.class))).thenReturn(false);
        when(memberRepository.save((any(Member.class)))).thenReturn(exceptedMember);

        Member joinedMember = memberService.join(username, password, name);

        //then
        assertThat(joinedMember).isNotNull();
        assertThat(joinedMember.getUsername()).isEqualTo(exceptedMember.getUsername());
        assertThat(joinedMember.getName()).isEqualTo(exceptedMember.getName());
        assertThat(joinedMember.getPassword()).isEqualTo(exceptedMember.getPassword());
    }
}
