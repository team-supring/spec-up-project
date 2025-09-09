package com.supring.specup.service;

import com.supring.specup.domain.User;
import com.supring.specup.dto.UpdateProfileRequest;
import com.supring.specup.dto.UserDto;
import com.supring.specup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDto getProfile(String memberId) {
        User user = userRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return UserDto.builder()
                .memberId(user.getMemberId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .profileImage(user.getProfileImage())
                .regionId(user.getRegionId())
                .createdAt(user.getCreatedAt())
                // 단일 role 필드를 Set<Role>로 변환
                .roles(Set.of(user.getRole()))
                .build();
    }

    @Override
    @Transactional
    public void updateProfile(String memberId, UpdateProfileRequest request) {
        User user = userRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setProfileImage(request.getProfileImage());
        // 필요한 다른 필드 업데이트
        userRepository.save(user);
    }
}
