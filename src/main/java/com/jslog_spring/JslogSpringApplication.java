package com.jslog_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JslogSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(JslogSpringApplication.class, args);
    }
//TODO : 엔티티 리펙토링 BaseEntity에 @Id 제거 후 각 엔티티에서 @Id를 선언하도록 변경
}
