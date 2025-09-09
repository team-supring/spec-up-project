package com.supring.specup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private String cookie; // refreshToken
    private String accessToken;
}
