package com.supring.specup.controller;

import com.supring.specup.dto.FaqDto;
import com.supring.specup.dto.FaqResponse;
import com.supring.specup.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<FaqDto>> getAllFaqs() {
        return ResponseEntity.ok(faqService.findAll());
    }

    // 페이징 조회
    @GetMapping("/page")
    public ResponseEntity<Page<FaqResponse>> getFaqList(
            @RequestParam(defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<FaqResponse> faqPage = faqService.getFaqList(pageable);
        return ResponseEntity.ok(faqPage);
    }

    // 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<FaqDto> getFaq(@PathVariable Long id) {
        return ResponseEntity.ok(faqService.findById(id));
    }

    // 생성
    @PostMapping
    public ResponseEntity<FaqDto> createFaq(@RequestBody FaqDto req) {
        FaqDto created = faqService.create(req.getQuestion(), req.getAnswer());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFaq(@PathVariable Long id, @RequestBody FaqDto req) {
        faqService.update(id, req.getQuestion(), req.getAnswer());
        return ResponseEntity.noContent().build();
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        faqService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
