package com.jslog_spring.global.rq;

import com.jslog_spring.global.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Rq {
    @Value("${app.admin-api-key}")
    private String adminApiKey;

    private final HttpServletRequest req;
    private final HttpServletResponse resp;

    public void checkAdminAuth() {
        String headerAuthorization = req.getHeader("Authorization");

        if (headerAuthorization == null || headerAuthorization.isBlank())
            throw new ServiceException("401-1", "Authorization 헤더가 존재하지 않습니다.");

        if (!headerAuthorization.startsWith("Bearer "))
            throw new ServiceException("401-2", "Authorization 헤더가 Bearer 형식이 아닙니다.");

        String providedApiKey = headerAuthorization.substring("Bearer ".length()).trim();

        if (!adminApiKey.equals(providedApiKey)) {
            throw new ServiceException("401-3", "관리자 API 키가 유효하지 않습니다.");
        }
        // 인증 성공 시 아무것도 반환하지 않음
    }

    // 선택적으로, boolean을 반환하는 메서드를 만들 수도 있습니다.
    public boolean isAuthenticatedAdmin() {
        String headerAuthorization = req.getHeader("Authorization");

        if (headerAuthorization == null || headerAuthorization.isBlank()) return false;
        if (!headerAuthorization.startsWith("Bearer ")) return false;

        String providedApiKey = headerAuthorization.substring("Bearer ".length()).trim();
        return adminApiKey.equals(providedApiKey);
    }

}
