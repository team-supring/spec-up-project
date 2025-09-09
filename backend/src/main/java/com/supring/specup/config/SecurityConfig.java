package com.supring.specup.config;

import com.supring.specup.domain.Role;
import com.supring.specup.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 1) WebSecurityCustomizer 에서 정적 리소스 및 WS 엔드포인트 무시
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring()
                .requestMatchers("/ws/**")
                .requestMatchers("/static/**") // 정적 리소스
                .requestMatchers("/*.js", "/*.css"); // 루트 폴더에 있는 JS/CSS
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    // 2) 동적 API에 대해서만 SecurityFilterChain 적용
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers("/api/auth/**").permitAll() // 허용
                        .requestMatchers("/api/faq/**").permitAll()
                        .requestMatchers("/db-test").permitAll()
                        // Swagger/OpenAPI 허용
                        .requestMatchers("/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/cs/**",
                                "/api/marketplace/**",
                                "/api/region/**")
                        .authenticated()
                        .anyRequest().denyAll())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public static class JwtAuthenticationFilter extends OncePerRequestFilter {
        private final JwtUtil jwtUtil;

        public JwtAuthenticationFilter(JwtUtil jwtUtil) {
            this.jwtUtil = jwtUtil;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain)
                throws ServletException, IOException {
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                if (jwtUtil.validateToken(token)) {
                    String memberId = jwtUtil.extractMemberId(token);
                    List<String> roles = jwtUtil.extractRoles(token);
                    List<GrantedAuthority> authorities = roles.stream()
                            .map(String::toUpperCase)
                            .map(Role::valueOf)
                            .map(role -> new SimpleGrantedAuthority(role.authority()))
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(memberId, null,
                            authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(request, response);
        }
    }
}
