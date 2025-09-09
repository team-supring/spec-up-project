package com.supring.specup.service;

import com.supring.specup.dto.UpdateProfileRequest;
import com.supring.specup.dto.UserDto;

public interface UserService {
    UserDto getProfile(String username);

    void updateProfile(String username, UpdateProfileRequest request);
}
