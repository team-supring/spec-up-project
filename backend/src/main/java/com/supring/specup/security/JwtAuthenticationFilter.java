package com.supring.specup.security;

import com.supring.specup.domain.Role;
import com.supring.specup.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Date;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    private static final AntPathRequestMatcher[] skipMatchers = {
            new AntPathRequestMatcher("/api/auth/**")
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 로그인/회원가입 경로 예외 처리
        for (AntPathRequestMatcher matcher : skipMatchers) {
            if (matcher.matches(request)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String header = request.getHeader("Authorization");

        System.out.println("▶ URI: " + request.getRequestURI());
        System.out.println("▶ Authorization header: " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                // 1) 토큰 만료 시간 조회 (안전한 방식)
                Date expiration = jwtUtil.getExpirationDate(token);
                System.out.println("▶ Token expiration: " + expiration);

                // 2) 현재 서버 시각
                Date now = new Date();
                System.out.println("▶ Now: " + now);

                // 3) 토큰 검증 결과
                boolean valid = jwtUtil.validateToken(token);
                System.out.println("▶ Token valid: " + valid);

                if (valid) {
                    String memberId = jwtUtil.extractMemberId(token);
                    List<String> roles = jwtUtil.extractRoles(token);
                    List<GrantedAuthority> authorities = roles.stream()
                            .map(String::toUpperCase)
                            .map(Role::valueOf)
                            .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.authority()))
                            .collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(memberId, null,
                            authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (io.jsonwebtoken.ExpiredJwtException eje) {
                System.err.println("JWT expired: " + eje.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access token expired");
                return;
            } catch (io.jsonwebtoken.SignatureException se) {
                System.err.println("JWT signature error: " + se.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token signature");
                return;
            } catch (Exception e) {
                System.err.println("JWT validation error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token validation failed");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
