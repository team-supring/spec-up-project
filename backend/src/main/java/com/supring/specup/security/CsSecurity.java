package com.supring.specup.security;

import com.supring.specup.domain.User;
import com.supring.specup.repository.CsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CsSecurity {
    private final CsRepository csRepository;

    /**
     * 게시글 소유자인지 검사
     *
     * @param userId 로그인한 사용자의 고유 ID
     * @param csId   확인할 게시글 ID
     */
    public boolean isOwner(Long userId, Long csId) {
        return csRepository.findById(csId)
                .filter(cs -> cs.getOwner().getUserId().equals(userId))
                .isPresent();
    }
}