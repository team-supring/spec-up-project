package com.supring.specup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supring.specup.domain.Cs;
import com.supring.specup.dto.CsDto;
import com.supring.specup.dto.CsInquiryDto;
import com.supring.specup.dto.CsRequest;
import com.supring.specup.dto.CsResponseDto;
import com.supring.specup.repository.CsRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.supring.specup.domain.User;
import com.supring.specup.repository.UserRepository;
import com.supring.specup.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsServiceImpl implements CsService {

    private final CsRepository csRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = true)
    public CsDto get(Long csId) {
        // 기존 get 로직 (토큰 추출 없이 실행)
        Cs cs = csRepository.findById(csId)
                .orElseThrow(() -> new RuntimeException("CS 글을 찾을 수 없습니다."));
        return CsDto.of(cs);
    }

    @Override
    @Transactional(readOnly = true)
    public CsDto getIfOwner(String memberId, Long csId) {
        Cs cs = csRepository.findById(csId)
                .orElseThrow(() -> new RuntimeException("CS 글을 찾을 수 없습니다."));

        // 전달받은 memberId 로 유저 조회
        User currentUser = userRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 관리자 권한 허용
        if (currentUser.getRole() == Role.ADMIN) {
            return CsDto.of(cs);
        }

        // 작성자와 동일해야 접근 허용
        if (!cs.getOwner().getMemberId().equals(memberId)) {
            throw new RuntimeException("본인 글이 아닙니다.");
        }

        return CsDto.of(cs);
    }

    @Override
    @Transactional
    public CsDto create(CsRequest req) {
        User owner = userRepository.findById(req.getOwnerId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        String photoJson;
        try {
            photoJson = objectMapper.writeValueAsString(req.getPhoto());
        } catch (Exception e) {
            throw new RuntimeException("사진 JSON 직렬화 실패", e);
        }

        Cs cs = Cs.builder()
                .owner(owner)
                .title(req.getTitle())
                .content(req.getContent())
                .photo(photoJson)
                .csAnswerYN("N")
                .createdAt(LocalDateTime.now())
                .build();

        return CsDto.of(csRepository.save(cs));
    }

    @Override
    @Transactional
    public CsDto create(String memberId, CsRequest request) {
        // username으로 User 찾기
        User owner = userRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        String photoJson;
        try {
            photoJson = objectMapper.writeValueAsString(request.getPhoto());
        } catch (Exception e) {
            throw new RuntimeException("사진 JSON 직렬화 실패", e);
        }

        Cs cs = Cs.builder()
                .owner(owner)
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .photo(photoJson)
                .csAnswerYN("N")
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

        try {
            cs.setPhoto(objectMapper.writeValueAsString(request.getPhoto()));
        } catch (Exception e) {
            throw new RuntimeException("사진 JSON 직렬화 실패", e);
        }

        csRepository.save(cs);
    }

    @Override
    @Transactional
    public void delete(Long csId) {
        csRepository.deleteById(csId);
    }

    @Override
    @Transactional
    public CsDto createAdminAnswer(Long csId, String answer, String adminId) {
        Cs cs = csRepository.findById(csId)
                .orElseThrow(() -> new RuntimeException("CS 글을 찾을 수 없습니다."));
        cs.setCsAnswer(answer);
        cs.setCsAnswerYN("Y");
        cs.setAnsweredBy(adminId);
        cs.setRepliedAt(LocalDateTime.now());
        Cs saved = csRepository.save(cs);
        return CsDto.of(saved);
    }

    @Override
    @Transactional
    public CsDto updateAdminAnswer(Long csId, String answer, String adminId) {
        Cs cs = csRepository.findById(csId)
                .orElseThrow(() -> new RuntimeException("CS 글을 찾을 수 없습니다."));
        cs.setCsAnswer(answer);
        cs.setAnsweredBy(adminId);
        cs.setRepliedAt(LocalDateTime.now());
        Cs saved = csRepository.save(cs);
        return CsDto.of(saved);
    }

    @Override
    @Transactional
    public void deleteAdminAnswer(Long csId, String adminId) {
        Cs cs = csRepository.findById(csId)
                .orElseThrow(() -> new RuntimeException("CS 글을 찾을 수 없습니다."));
        cs.setCsAnswer(null);
        cs.setCsAnswerYN("N");
        cs.setAnsweredBy(null);
        cs.setRepliedAt(null);
        csRepository.save(cs);
    }

    @Override
    public Page<CsDto> listAll(PageRequest pageRequest) {
        return csRepository.findAll(pageRequest)
                .map(CsDto::of);
    }

    @Override
    public Page<CsDto> listByOwner(Long ownerId, PageRequest pageRequest) {
        return csRepository.findByOwnerUserIdOrderByCreatedAtDesc(ownerId, pageRequest)
                .map(CsDto::of);
    }

    @Override
    public CsDto getById(Long csId) {
        Cs cs = csRepository.findById(csId)
                .orElseThrow(() -> new NoSuchElementException("문의 없음"));
        return CsDto.of(cs);
    }

}
