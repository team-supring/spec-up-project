package com.supring.specup.controller;

import com.supring.specup.dto.UpdateProfileRequest;
import com.supring.specup.dto.UserDto;
import com.supring.specup.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class UserController {

    private final UserService userService;

    @Operation(summary = "내 프로필 조회")
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(Authentication auth) {
        UserDto dto = userService.getProfile(auth.getName());
        return ResponseEntity.ok(dto); // 200 OK
    }

    @Operation(summary = "내 프로필 수정")
    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody UpdateProfileRequest req,
            Authentication auth) {
        userService.updateProfile(auth.getName(), req);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
