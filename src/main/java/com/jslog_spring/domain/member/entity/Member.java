package com.jslog_spring.domain.member.entity;

import com.jslog_spring.domain.member.exception.InvalidInputValueException;
import com.jslog_spring.domain.post.entity.Post;
import com.jslog_spring.domain.post.exception.PostNotFoundException;
import com.jslog_spring.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String username, String password, String name, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.posts = new ArrayList<>();
    }

    public static Member create(String username, String password, String name, String role) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (role == null || role.isBlank()) {
            role = "ROLE_USER";
        }

        return Member.builder()
                .username(username)
                .password(password)
                .name(name)
                .role(role)
                .build();
    }

    public static Member create(String username, String password, String name) {
        return create(username, password, name, "ROLE_USER");
    }

    public void updateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputValueException();
        }
        this.name = name;
    }

    public Post createPost(String title, String content) {
        Post newPost = Post.create(this, title, content);
        this.posts.add(newPost);
        return newPost;
    }

    public void deletePost(Post post) {
        if (post == null || !this.posts.contains(post)) {
            throw new PostNotFoundException();
        }
        this.posts.remove(post);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        if (this.id == null || member.id == null) {
            return false;
        }
        return this.id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
