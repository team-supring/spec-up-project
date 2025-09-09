package com.supring.specup.service;

import com.supring.specup.dto.FaqDto;
import com.supring.specup.dto.FaqRequest;

import java.util.List;

public interface FaqService {
    List<FaqDto> findAll();

    FaqDto findById(Long id);

    FaqDto create(String question, String answer);

    void update(Long id, String question, String answer);

    void delete(Long id);
}
