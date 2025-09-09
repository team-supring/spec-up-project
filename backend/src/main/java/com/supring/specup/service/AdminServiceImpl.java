package com.supring.specup.service;

import com.supring.specup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
