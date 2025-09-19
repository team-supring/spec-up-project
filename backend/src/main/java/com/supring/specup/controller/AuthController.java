package com.supring.specup.controller;

import com.supring.specup.dto.CommonResponse;
import com.supring.specup.dto.LoginRequest;
import com.supring.specup.dto.SignupRequest;
import com.supring.specup.dto.TokenResponse;
import com.supring.specup.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;

        @PostMapping("/signup")
        public ResponseEntity<CommonResponse> signup(@RequestBody SignupRequest req) {
                authService.signup(req);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new CommonResponse(true, "회원가입 성공"));
        }

        @PostMapping("/login")
        public ResponseEntity<TokenResponse> login(
                        @RequestBody LoginRequest req,
                        HttpServletResponse resp) {

                TokenResponse tokens = authService.login(req);

                ResponseCookie cookie = ResponseCookie.from("RefreshToken", tokens.getRefreshToken())
                                .httpOnly(true)
                                .path("/")
                                .maxAge(tokens.getRefreshExpiry())
                                .build();
                resp.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

                return ResponseEntity.ok()
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.getAccessToken())
                                .body(tokens);
        }

        @PostMapping("/logout")
        public ResponseEntity<Void> logout(
                        @CookieValue(name = "RefreshToken", required = false) String refreshToken,
                        HttpServletResponse resp) {

                if (refreshToken != null) {
                        authService.logout(refreshToken);
                        ResponseCookie cookie = ResponseCookie.from("RefreshToken", "")
                                        .httpOnly(true)
                                        .path("/")
                                        .maxAge(0)
                                        .build();
                        resp.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                }
                return ResponseEntity.noContent().build();
        }

        @PostMapping("/reissue")
        public ResponseEntity<TokenResponse> reissue(
                        @CookieValue(name = "RefreshToken", required = false) String refreshToken,
                        HttpServletResponse resp) {
                System.out.println("Reissue called with refreshToken = " + refreshToken);
                TokenResponse tokens = authService.reissue(refreshToken);

                ResponseCookie cookie = ResponseCookie.from("RefreshToken", tokens.getRefreshToken())
                                .httpOnly(true)
                                .path("/")
                                .maxAge(tokens.getRefreshExpiry())
                                .build();
                resp.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

                return ResponseEntity.ok()
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.getAccessToken())
                                .body(tokens);
        }

        @GetMapping("/checkId")
        public ResponseEntity<Map<String, Boolean>> checkId(@RequestParam String memberId) {
                boolean available = authService.checkId(memberId);
                return ResponseEntity.ok(Collections.singletonMap("available", available));
        }
}
