package com.supring.specup.dto;

import com.supring.specup.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
public class UserDto {
    private String memberId;
    private String email;
    private String phone;
    private String profileImage;
    private Long regionId;
    private LocalDateTime createdAt;
    private Set<Role> roles;
}
