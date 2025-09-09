package com.supring.specup.controller;

import com.supring.specup.dto.CommonResponse;
import com.supring.specup.dto.LoginRequest;
import com.supring.specup.dto.SignupRequest;
import com.supring.specup.dto.TokenResponse;
import com.supring.specup.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        TokenResponse tokens = authService.login(req);
        return ResponseEntity.ok()
                .header("refreshToken", tokens.getCookie())
                .header("accessToken", tokens.getAccessToken())
                .body(tokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("refreshToken") String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestHeader("refreshToken") String refreshToken) {
        TokenResponse tokens = authService.reissue(refreshToken);
        return ResponseEntity.ok()
                .header("accessToken", tokens.getAccessToken())
                .header("refreshToken", tokens.getCookie())
                .body(tokens);
    }

    @GetMapping("/checkId")
    public ResponseEntity<Boolean> checkId(@RequestParam String memberId) {
        boolean available = authService.checkId(memberId);
        return ResponseEntity.ok(available);
    }
}
