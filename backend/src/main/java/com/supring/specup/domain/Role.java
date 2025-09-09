package com.supring.specup.domain;

public enum Role {
    USER, // DB에 'USER' 로 저장
    ADMIN, // DB에 'ADMIN'으로 저장
    GUEST; // DB에 'GUEST'로 저장

    // Spring Security용 권한 문자열 반환
    public String authority() {
        return "ROLE_" + this.name();
    }
}
