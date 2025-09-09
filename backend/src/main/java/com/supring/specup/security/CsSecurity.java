package com.supring.specup.security;

import com.supring.specup.domain.Cs;
import com.supring.specup.repository.CsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CsSecurity {

    private final CsRepository csRepository;

    public boolean isOwner(String username, Long csId) {
        return csRepository.findById(csId)
                .map(Cs::getWriter)
                .map(writer -> writer.equals(username))
                .orElse(false);
    }
}
