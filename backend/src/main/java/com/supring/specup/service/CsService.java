package com.supring.specup.service;

import com.supring.specup.dto.CsDto;
import com.supring.specup.dto.CsRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CsService {
    // 1:1 문의
    CsDto get(Long csId);

    // 본인 확인용 상세
    CsDto getIfOwner(String username, Long csId);

    // 전체 문의 (관리자)
    List<CsDto> listAll();

    // 내 문의 내역 (로그인)
    List<CsDto> listByOwner(String memberId);

    // Admin용: 특정 사용자의 문의만 조회
    List<CsDto> listByOwnerForAdmin(String memberId);

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

}
