package com.jslog_spring.domain.member.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {
    @Test
    @DisplayName("username(아이디), password, name을 입력받아 Member 객체를 생성한다.")
    void memberCreationTest() {
        String username = "testUsername";
        String password = "testPassword";
        String name = "testName";

        Member member = Member.create(username, password, name);

        Assertions.assertThat(member).isNotNull();
        Assertions.assertThat(member.getUsername()).isEqualTo(username);
        Assertions.assertThat(member.getPassword()).isEqualTo(password);
        Assertions.assertThat(member.getName()).isEqualTo(name);
        Assertions.assertThat(member.getRole()).isEqualTo("ROLE_USER");
    }

    @Test
    @DisplayName("Member객체 생성 시 username이 null이거나 빈 문자열일 경우 예외가 발생한다.")
    void memberCreationInvalidUsernameTest() {
        String password = "testPassword";
        String name = "testName";

        Assertions.assertThatThrownBy(() -> Member.create(null, password, name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Username cannot be empty.");

        Assertions.assertThatThrownBy(() -> Member.create("", password, name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Username cannot be empty.");
    }

    @Test
    @DisplayName("Member객체 생성 시 password가 null이거나 빈 문자열일 경우 예외가 발생한다.")
    void memberCreationInvalidPasswordTest() {
        String username = "testUsername";
        String name = "testName";

        Assertions.assertThatThrownBy(() -> Member.create(username, null, name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password cannot be empty.");

        Assertions.assertThatThrownBy(() -> Member.create(username, "", name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Password cannot be empty.");
    }

    @Test
    @DisplayName("Member객체 생성 시 name이 null이거나 빈 문자열일 경우 예외가 발생한다.")
    void memberCreationInvalidNameTest() {
        String username = "testUsername";
        String password = "testPassword";

        Assertions.assertThatThrownBy(() -> Member.create(username, password, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name cannot be empty.");

        Assertions.assertThatThrownBy(() -> Member.create(username, password, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name cannot be empty.");
    }

    @Test
    @DisplayName("새 이름을 입력받아 Member 객체의 이름을 업데이트한다.")
    void memberUpdateTest() {
        String username = "testUsername";
        String password = "testPassword";
        String name = "testName";
        String newName = "updatedName";

        Member member = Member.create(username, password, name);

        member.updateName(newName);
        Assertions.assertThat(member.getName()).isEqualTo(newName);
    }
}