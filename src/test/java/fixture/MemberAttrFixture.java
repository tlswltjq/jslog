package fixture;

import com.jslog_spring.domain.member.entity.MemberAttr;

public class MemberAttrFixture {
    public static MemberAttr create(String username) {
        return MemberAttr.create(username);
    }

    public static MemberAttr create() {
        return MemberAttr.create("username");
    }
}
