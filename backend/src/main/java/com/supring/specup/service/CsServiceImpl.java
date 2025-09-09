package com.supring.specup.service;

import com.supring.specup.domain.Cs;
import com.supring.specup.dto.CsDto;
import com.supring.specup.dto.CsRequest;
import com.supring.specup.repository.CsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsServiceImpl implements CsService {

    private final CsRepository csRepository;

    @Override
    @Transactional(readOnly = true)
    public CsDto get(Long csId) {
        Cs cs = csRepository.findById(csId)
                .orElseThrow(() -> new RuntimeException("CS 글을 찾을 수 없습니다."));
        return CsDto.of(cs);
    }

    @Override
    @Transactional(readOnly = true)
    public CsDto getIfOwner(String username, Long csId) {
        Cs cs = csRepository.findById(csId)
                .filter(c -> c.getWriter().equals(username))
                .orElseThrow(() -> new RuntimeException("본인 글이 아닙니다."));
        return CsDto.of(cs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CsDto> listAll() {
        return csRepository.findAll().stream()
                .map(CsDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CsDto> listByOwner(String username) {
        return csRepository.findByWriter(username).stream()
                .map(CsDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CsDto create(String username, CsRequest request) {
        Cs cs = Cs.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(username)
                .createdAt(LocalDateTime.now())
                .build();
        Cs saved = csRepository.save(cs);
        return CsDto.of(saved);
    }

    @Override
    @Transactional
    public void update(Long csId, CsRequest request) {
        Cs cs = csRepository.findById(csId)
                .orElseThrow(() -> new RuntimeException("CS 글을 찾을 수 없습니다."));
        cs.setTitle(request.getTitle());
        cs.setContent(request.getContent());
        csRepository.save(cs);
    }

    @Override
    @Transactional
    public void delete(Long csId) {
        csRepository.deleteById(csId);
    }
}
