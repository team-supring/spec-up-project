package com.supring.specup.repository;

import com.supring.specup.domain.Cs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CsRepository extends JpaRepository<Cs, Long> {

    // 1) 사용자 자신의 문의 조회 (최신순)
    Page<Cs> findByOwnerUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    // 2) 관리자 전체 문의 조회 (페이징 없이, 최신순)
    List<Cs> findAllByOrderByCreatedAtDesc();

    // 3) 관리자 전체 문의 조회 (페이지 단위)
    Page<Cs> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 4) 사용자별 “답변 완료” 문의 조회
    List<Cs> findByOwnerUserIdAndCsAnswerYN(Long userId, String csAnswerYN);

    Page<Cs> findAll(Pageable pageable);

}
