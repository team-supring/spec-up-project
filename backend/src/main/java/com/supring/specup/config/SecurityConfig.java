package com.supring.specup.config;

import com.supring.specup.security.JwtAuthenticationFilter;
import com.supring.specup.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        private final JwtUtil jwtUtil;

        public SecurityConfig(JwtUtil jwtUtil) {
                this.jwtUtil = jwtUtil;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                // 개발용 간단 모드 (실서비스는 BCryptPasswordEncoder 권장)
                return NoOpPasswordEncoder.getInstance();
                // return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/api/faq/**").permitAll()

                                                // Swagger
                                                .requestMatchers(
                                                                "/swagger-ui.html",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs",
                                                                "/v3/api-docs/**")
                                                .permitAll()

                                                // 고객센터 인가
                                                .requestMatchers("/api/support/faq/**").permitAll() // FAQ 공개
                                                .requestMatchers("/api/support/inquiry").hasRole("ADMIN") // GET
                                                                                                          // /api/support/inquiry
                                                .requestMatchers("/api/support/inquiry/user/**").hasRole("ADMIN")
                                                .requestMatchers("/api/support/**").authenticated() // 나머지 모두 인증

                                                // 기타
                                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                                                .requestMatchers("/api/community/**").hasRole("USER")
                                                .anyRequest().denyAll())

                                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint((request, response, authException) -> response
                                                                .sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                                                                "Unauthorized")))
                                // 외부 클래스 JwtAuthenticationFilter를 사용

                                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil),
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
