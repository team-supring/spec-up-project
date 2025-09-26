package com.supring.specup.service;

import com.supring.specup.dto.CsDto;
import com.supring.specup.dto.CsInquiryDto;
import com.supring.specup.dto.CsRequest;
import com.supring.specup.dto.CsResponseDto;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CsService {
    // 1:1 문의
    CsDto get(Long csId);

    // 본인 확인용 상세
    CsDto getIfOwner(String username, Long csId);;

    // 문의 등록
    CsDto create(String username, CsRequest request);

    // 문의 수정
    void update(Long csId, CsRequest request);

    // 문의 삭제
    void delete(Long csId);

    // 관리자 답변 등록
    CsDto createAdminAnswer(Long csId, String answer, String adminId);

    // 관리자 답변 수정
    CsDto updateAdminAnswer(Long csId, String answer, String adminId);

    // 관리자 답변 삭제
    void deleteAdminAnswer(Long csId, String adminId);

    CsDto create(CsRequest req);

    Page<CsDto> listAll(PageRequest pageRequest);

    Page<CsDto> listByOwner(Long ownerId, PageRequest pageRequest);

    CsDto getById(Long csId);

}
