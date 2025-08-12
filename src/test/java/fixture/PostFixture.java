package fixture;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.domain.post.entity.Post;
import org.springframework.test.util.ReflectionTestUtils;

public class PostFixture {

    public static Post createPost(Member member) {
        return Post.create(member, "Test Title", "Test Content");
    }


    public static Post createPost(Member member, String title, String content) {
        return Post.create(member, title, content);
    }

    public static Post createPostWithId(Long id, Member member) {
        Post post = createPost(member);
        ReflectionTestUtils.setField(post, "id", id);
        return post;
    }

    public static Post createPostWithId(Long id, Member member, String title, String content) {
        Post post = createPost(member, title, content);
        ReflectionTestUtils.setField(post, "id", id);
        return post;
    }
}
