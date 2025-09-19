package com.supring.specup.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String refreshToken;
    private String accessToken;
    private long refreshExpiry; // 초 단위

    // 편의 메서드
    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getRefreshExpiry() {
        return refreshExpiry;
    }
}
