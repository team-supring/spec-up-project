package com.supring.specup.service;

import com.supring.specup.dto.FaqDto;
import com.supring.specup.dto.FaqResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FaqService {
    List<FaqDto> findAll();

    FaqDto findById(Long id);

    FaqDto create(String question, String answer);

    void update(Long id, String question, String answer);

    void delete(Long id);

    Page<FaqResponse> getFaqList(Pageable pageable);
}
