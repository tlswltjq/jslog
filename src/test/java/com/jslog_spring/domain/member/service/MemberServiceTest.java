package com.jslog_spring.domain.member.service;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.member.exception.InvalidInputValueException;
import com.jslog_spring.domain.member.exception.MemberNotFoundException;
import com.jslog_spring.domain.member.exception.UserNameDuplicationException;
import com.jslog_spring.domain.member.repository.MemberRepository;
import com.jslog_spring.global.security.JwtUtil;
import fixture.MemberFixture;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.jslog_spring.global.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.jslog_spring.global.exception.ErrorCode.USERNAME_DUPLICATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("signIn 성공 시, accessToken과 refreshToken을 반환하고 Redis에 refreshToken을 저장한다")
    void signInSuccess() {
        // given
        String username = "test@example.com";
        String password = "password";
        String accessToken = "access-token";
        String refreshToken = "refresh-token";

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtil.generateAccessToken(anyMap())).thenReturn(accessToken);
        when(jwtUtil.generateRefreshToken(anyMap())).thenReturn(refreshToken);

        ValueOperations<String, String> valueOps = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);

        // when
        Pair<String, String> result = memberService.signIn(username, password);

        // then
        assertThat(result.getFirst()).isEqualTo(accessToken);
        assertThat(result.getSecond()).isEqualTo(refreshToken);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateAccessToken(anyMap());
        verify(jwtUtil).generateRefreshToken(anyMap());
        verify(valueOps).set(eq(username), eq(refreshToken), eq(365L), eq(TimeUnit.DAYS));
    }

    @Test
    @DisplayName("signIn 실패 시, 예외를 반환한다")
    void signInFailure() {
        // given
        String username = "testUsername";
        String password = "wrongPassword";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        assertThatThrownBy(() -> {
            memberService.signIn(username, password);
        })
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    @DisplayName("refreshToken이 유효하고 Redis에 저장된 토큰과 일치하면 새로운 accessToken과 refreshToken을 발급한다")
    void reissueTest() {
        // given
        String username = "testUser";
        String oldRefreshToken = "oldRefreshToken";

        String expectedAccessToken = "newAccessToken";
        String expectedRefreshToken = "newRefreshToken";

        Claims claims = mock(Claims.class);

        ValueOperations<String, String> valueOps = mock(ValueOperations.class);

        when(jwtUtil.validateToken(any(String.class))).thenReturn(true);
        when(jwtUtil.getClaims(any(String.class))).thenReturn(claims);

        when(claims.get("username", String.class)).thenReturn(username);

        when(redisTemplate.opsForValue()).thenReturn(valueOps);
        when(valueOps.get(any(String.class))).thenReturn(oldRefreshToken);

        when(jwtUtil.generateAccessToken(anyMap())).thenReturn(expectedAccessToken);
        when(jwtUtil.generateRefreshToken(anyMap())).thenReturn(expectedRefreshToken);

        Pair<String, String> reissued = memberService.reissue(oldRefreshToken);

        verify(jwtUtil).validateToken(oldRefreshToken);
        verify(jwtUtil).getClaims(oldRefreshToken);
        verify(valueOps).get(username);
        verify(jwtUtil).generateAccessToken(Map.of("username", username));
        verify(jwtUtil).generateRefreshToken(Map.of("username", username));
        verify(valueOps).set(eq(username), eq(expectedRefreshToken), eq(365L), eq(TimeUnit.DAYS));

        assertThat(reissued.getFirst()).isEqualTo(expectedAccessToken);
        assertThat(reissued.getSecond()).isEqualTo(expectedRefreshToken);
    }
}
