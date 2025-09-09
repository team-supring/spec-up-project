package com.supring.specup.repository;

import com.supring.specup.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);

    // 추가: 회원 ID로 기존 리프레시 토큰 삭제
    void deleteByMemberId(String memberId);
}
