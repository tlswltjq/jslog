package fixture;

import com.jslog_spring.domain.member.entity.Member;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {

    public static Member createMember() {
        return Member.create("testUser", "test@example.com", "password");
    }

    public static Member createMember(String username, String password, String name) {
        return Member.create(username, password, name);
    }

    public static Member createMemberWithId(Long id) {
        Member member = createMember();
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }
}
