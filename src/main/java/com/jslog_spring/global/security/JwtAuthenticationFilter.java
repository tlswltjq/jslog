package com.jslog_spring.global.security;

import com.jslog_spring.domain.member.entity.Member;
import com.jslog_spring.global.exception.ServiceException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.substring(7);// "Bearer " 제거

        try {
            if (jwtUtil.validateToken(token)) {
                String username = jwtUtil.getClaims(token).get("username", String.class);
                Member member = (Member) userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (ServiceException e) {
            SecurityContextHolder.clearContext();
            log.warn("JWT 인증 실패: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
