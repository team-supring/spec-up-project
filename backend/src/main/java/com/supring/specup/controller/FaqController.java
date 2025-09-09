package com.supring.specup.controller;

import com.supring.specup.dto.FaqDto;
import com.supring.specup.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @GetMapping
    public ResponseEntity<List<FaqDto>> getAllFaqs() {
        return ResponseEntity.ok(faqService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaqDto> getFaq(@PathVariable Long id) {
        return ResponseEntity.ok(faqService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FaqDto> createFaq(@RequestBody FaqDto req) {
        FaqDto created = faqService.create(req.getQuestion(), req.getAnswer());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFaq(@PathVariable Long id, @RequestBody FaqDto req) {
        faqService.update(id, req.getQuestion(), req.getAnswer());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        faqService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
