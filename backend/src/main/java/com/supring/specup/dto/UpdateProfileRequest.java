package com.supring.specup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {
    private String email;
    private String phone;
    private String profileImage;
    // 필요 시 다른 필드 추가
}
