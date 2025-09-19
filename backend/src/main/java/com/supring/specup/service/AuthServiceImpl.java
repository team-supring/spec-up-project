package com.supring.specup.service;

import com.supring.specup.domain.RefreshToken;
import com.supring.specup.domain.User;
import com.supring.specup.domain.Role;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

        String birth = request.getBirth();
        if (birth == null || !birth.matches("\\d{6}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "생년월일은 yyMMdd 형식의 6자리 숫자여야 합니다");
        }

        // yy, MM, dd 분리
        int yy = Integer.parseInt(birth.substring(0, 2));
        int mm = Integer.parseInt(birth.substring(2, 4));
        int dd = Integer.parseInt(birth.substring(4, 6));

        // 연도 결정: (현재 연도 % 100) 보다 크면 1900~, 작거나 같으면 2000~ 로 가정
        int currentYearLast2 = LocalDate.now().getYear() % 100;
        int year = (yy > currentYearLast2 ? 1900 : 2000) + yy;

        LocalDate birthDate;
        try {
            birthDate = LocalDate.of(year, mm, dd);
        } catch (DateTimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효한 생년월일이 아닙니다");
        }

        if (birthDate.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "생년월일이 오늘 이후일 수 없습니다");
        }

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
                .role(Role.USER) // 요청에서 Role을 받지 않고 USER로 고정
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
        RefreshToken entity = RefreshToken.builder()
                .token(refreshToken)
                .memberId(user.getMemberId())
                .createdAt(LocalDateTime.now())
                .build();
        tokenRepository.save(entity);

        return TokenResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .refreshExpiry(jwtUtil.getRefreshExpiry() / 1000) // seconds
                .build();
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        tokenRepository.deleteByToken(refreshToken);
    }

    @Override
    @Transactional
    public TokenResponse reissue(String oldRefresh) {
        if (oldRefresh == null || !jwtUtil.validateToken(oldRefresh)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다");
        }

        RefreshToken stored = tokenRepository.findByToken(oldRefresh)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "저장된 리프레시 토큰이 없습니다"));

        String memberId = jwtUtil.extractMemberId(oldRefresh);
        List<String> roles = userRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"))
                .getRoleList();

        String newAccess = jwtUtil.generateAccessToken(memberId, roles);
        String newRefresh = jwtUtil.generateRefreshToken(memberId);

        stored.updateToken(newRefresh);
        tokenRepository.save(stored);

        return TokenResponse.builder()
                .refreshToken(newRefresh)
                .accessToken(newAccess)
                .refreshExpiry(jwtUtil.getRefreshExpiry() / 1000)
                .build();
    }

    @Override
    public boolean checkId(String memberId) {
        return !userRepository.existsByMemberId(memberId);
    }
}
