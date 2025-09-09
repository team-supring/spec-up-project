package com.supring.specup.service;

import com.supring.specup.domain.RefreshToken;
import com.supring.specup.domain.Role;
import com.supring.specup.domain.User;
import com.supring.specup.dto.LoginRequest;
import com.supring.specup.dto.SignupRequest;
import com.supring.specup.dto.TokenResponse;
import com.supring.specup.repository.RefreshTokenRepository;
import com.supring.specup.repository.UserRepository;
import com.supring.specup.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository tokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignupRequest request) {
        if (userRepository.existsByMemberId(request.getMemberId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 ID");
        }
        User user = User.builder()
                .memberId(request.getMemberId())
                .password(passwordEncoder.encode(request.getMemberPassword()))
                .name(request.getName())
                .email(request.getEmail())
                .birth(request.getBirth())
                .sex(request.getSex())
                .role(Role.USER)
                .regionId(request.getRegionId())
                .tradePoint(0.0)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByMemberId(request.getMemberId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"));
        if (!passwordEncoder.matches(request.getMemberPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다");
        }

        List<String> roles = user.getRoleList();
        String accessToken = jwtUtil.generateAccessToken(user.getMemberId(), roles);
        String refreshToken = jwtUtil.generateRefreshToken(user.getMemberId());

        tokenRepository.deleteByMemberId(user.getMemberId());
        tokenRepository.save(new RefreshToken(null, refreshToken, user.getMemberId(), LocalDateTime.now()));

        return new TokenResponse(refreshToken, accessToken);
    }

    @Override
    public void logout(String refreshToken) {
        tokenRepository.deleteByToken(refreshToken);
    }

    @Override
    public TokenResponse reissue(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "리프레시 토큰 만료 또는 유효하지 않음");
        }
        RefreshToken stored = tokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "저장된 리프레시 토큰이 없습니다"));
        String memberId = jwtUtil.extractMemberId(refreshToken);
        List<String> roles = userRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"))
                .getRoleList();

        String newAccess = jwtUtil.generateAccessToken(memberId, roles);
        return new TokenResponse(refreshToken, newAccess);
    }

    @Override
    public boolean checkId(String memberId) {
        return !userRepository.existsByMemberId(memberId);
    }
}
