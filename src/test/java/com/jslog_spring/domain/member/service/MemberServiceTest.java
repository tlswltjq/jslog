package com.jslog_spring.domain.member.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.exception.InvalidInputValueException;
import com.jslog_spring.domain.member.exception.MemberNotFoundException;
import com.jslog_spring.domain.member.exception.UserNameDuplicationException;
import com.jslog_spring.domain.member.repository.MemberRepository;
import com.jslog_spring.domain.todo.exception.TodoOwnershipException;
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

import java.util.Optional;

import static com.jslog_spring.global.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    @DisplayName("회원가입시 username(아이디)이 중복되면 예외가 발생한다.")
    void joinTest_UsernameDuplicationException() {
        //given
        String username = "already_exist@username.com";
        String password = "password";
        String name = "testUser";

        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(memberRepository.existsByUsername(any(String.class))).thenReturn(true);

        assertThatThrownBy(() -> {
            memberService.join(username, password, name);
        }).isInstanceOf(UserNameDuplicationException.class)
                .isInstanceOfSatisfying(UserNameDuplicationException.class, e -> {
                            assertThat(e.getErrorCode()).isEqualTo(USERNAME_DUPLICATION);
                        }
                );

    }

    @Test
    @DisplayName("username(아이디)를 이용해 사용자를 조회할 수 있다.")
    void getMemberTest() {
        String username = "testUsername";
        Member member = MemberFixture.createMember(username, "", "");

        when(memberRepository.findByUsername(any(String.class))).thenReturn(Optional.of(member));

        Member foundMember = memberService.getMember(username);

        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("조회결과가 없으면 예외가 발생한다.")
    void getMemberTest_MemberNotFoundException() {
        String username = "nonExistUsername";

        when(memberRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            memberService.getMember(username);
        }).isInstanceOf(MemberNotFoundException.class)
                .isInstanceOfSatisfying(MemberNotFoundException.class, e -> {
                            assertThat(e.getErrorCode()).isEqualTo(MEMBER_NOT_FOUND);
                        }
                );
    }

    @Test
    @DisplayName("사용자의 이름을 업데이트할 수 있다.")
    void updateUserNameTest() {
        Member member = MemberFixture.createMember();
        String newName = "newName";

        when(memberRepository.save(any(Member.class))).thenReturn(member);
        Member updated = memberService.updateUserName(member, newName);

        assertThat(updated.getUsername()).isEqualTo(member.getUsername());
        assertThat(updated.getPassword()).isEqualTo(member.getPassword());
        assertThat(updated.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("업데이트 하려는 이름이 null이거나 공백이면 예외가 발생한다.")
    void updateUserNameTest_InvalidInputValueException() {
        Member member = MemberFixture.createMember();

        assertThatThrownBy(() -> {
            memberService.updateUserName(member, null);
        }).isInstanceOf(InvalidInputValueException.class)
                .isInstanceOfSatisfying(InvalidInputValueException.class, e -> {
                            assertThat(e.getMessage()).isEqualTo("Invalid input value");
                        }
                );

        assertThatThrownBy(() -> {
            memberService.updateUserName(member, "   ");
        }).isInstanceOf(InvalidInputValueException.class)
                .isInstanceOfSatisfying(InvalidInputValueException.class, e -> {
                            assertThat(e.getMessage()).isEqualTo("Invalid input value");
                        }
                );
    }
}
