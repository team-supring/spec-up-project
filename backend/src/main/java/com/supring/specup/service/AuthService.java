package com.supring.specup.service;

import com.supring.specup.dto.LoginRequest;
import com.supring.specup.dto.SignupRequest;
import com.supring.specup.dto.TokenResponse;

public interface AuthService {
    void signup(SignupRequest request);

    TokenResponse login(LoginRequest request);

    void logout(String refreshToken);

    TokenResponse reissue(String refreshToken);

    boolean checkId(String memberId);
}
