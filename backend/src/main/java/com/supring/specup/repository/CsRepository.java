package com.supring.specup.repository;

import com.supring.specup.domain.Cs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CsRepository extends JpaRepository<Cs, Long> {

    // 1) 작성자(유저) 자신의 문의 조회
    List<Cs> findByOwnerMemberIdOrderByCreatedAtDesc(String memberId);

    // 2) 관리자 전체 문의 조회 (페이징 없이)
    List<Cs> findAllByOrderByCreatedAtDesc();

    // 3) 관리자 전체 문의 조회 (페이징 필요할 때)
    Page<Cs> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
