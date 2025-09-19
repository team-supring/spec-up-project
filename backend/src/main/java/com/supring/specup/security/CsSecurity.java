package com.supring.specup.security;

import com.supring.specup.domain.Cs;
import com.supring.specup.repository.CsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Component
@RequiredArgsConstructor
public class CsSecurity {

    private final CsRepository csRepository;

    public boolean isOwner(String username, Long csId) {
        return csRepository.findById(csId)
                .map(cs -> cs.getOwner().getName())
                .map(ownerUsername -> ownerUsername.equals(username))
                .orElse(false);
    }
}
