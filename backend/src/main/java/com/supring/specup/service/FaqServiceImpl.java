// 4. FaqServiceImpl.java
package com.supring.specup.service;

import com.supring.specup.domain.Faq;
import com.supring.specup.dto.FaqDto;
import com.supring.specup.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqRepository faqRepository;

    @Override
    public List<FaqDto> findAll() {
        return faqRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(f -> new FaqDto(
                        f.getId(),
                        f.getQuestion(),
                        f.getAnswer(),
                        f.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public FaqDto findById(Long id) {
        Faq f = faqRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ 없음"));
        return new FaqDto(
                f.getId(),
                f.getQuestion(),
                f.getAnswer(),
                f.getCreatedAt());
    }

    @Override
    @Transactional
    public FaqDto create(String question, String answer) {
        Faq f = Faq.builder()
                .question(question)
                .answer(answer)
                .createdAt(LocalDateTime.now())
                .build();
        Faq saved = faqRepository.save(f);
        return new FaqDto(
                saved.getId(),
                saved.getQuestion(),
                saved.getAnswer(),
                saved.getCreatedAt());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void update(Long id, String question, String answer) {
        Faq f = faqRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ 없음"));
        f.setQuestion(question);
        f.setAnswer(answer);
        faqRepository.save(f);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!faqRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FAQ 없음");
        }
        faqRepository.deleteById(id);
    }
}
