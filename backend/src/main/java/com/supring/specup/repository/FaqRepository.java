package com.supring.specup.repository;

import com.supring.specup.domain.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    // 생성일자 내림차순 전체 조회
    List<Faq> findAllByOrderByCreatedAtDesc();
}
