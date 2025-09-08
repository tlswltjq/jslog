package com.jslog_spring.domain.member.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {
    @Test
    @DisplayName("아이디, 비밀번호, 이름을 입력받아 Member 객체를 생성한다.")
    void memberCreation() {
        String username = "testUsername";
        String password = "testPassword";
        String name = "testName";

        Member member = Member.create(username, password, name);

        Assertions.assertThat(member).isNotNull();
        Assertions.assertThat(member.getUsername()).isEqualTo(username);
        Assertions.assertThat(member.getPassword()).isEqualTo(password);
        Assertions.assertThat(member.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("새 이름을 입력받아 Member 객체의 이름을 업데이트한다.")
    void memberUpdate() {
        String username = "testUsername";
        String password = "testPassword";
        String name = "testName";
        String newName = "updatedName";

        Member member = Member.create(username, password, name);

        member.updateName(newName);
        Assertions.assertThat(member.getName()).isEqualTo(newName);
    }
}