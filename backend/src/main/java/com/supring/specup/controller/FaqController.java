package com.supring.specup.controller;

import com.supring.specup.dto.FaqDto;
import com.supring.specup.dto.FaqRequest;
import com.supring.specup.dto.FaqResponse;
import com.supring.specup.service.FaqService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/support/faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    // --- FAQ Endpoints ---
    // 전체 조회
    @Operation(summary = "FAQ 목록 조회")
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

    @Operation(summary = "FAQ 상세 조회")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FaqDto> getFaq(@PathVariable Long id) {
        return ResponseEntity.ok(faqService.findById(id));
    }

    // 생성
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FaqDto> createFaq(@RequestBody FaqRequest req) {
        FaqDto created = faqService.create(req.getQuestion(), req.getAnswer());
        URI location = URI.create("/api/support/faq/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    // 수정
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateFaq(
            @PathVariable Long id,
            @RequestBody FaqRequest req) {
        faqService.update(id, req.getQuestion(), req.getAnswer());
        return ResponseEntity.noContent().build();
    }

    // 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        faqService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
