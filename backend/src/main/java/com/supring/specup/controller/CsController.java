package com.supring.specup.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.supring.specup.dto.CsDto;
import com.supring.specup.dto.CsRequest;
import com.supring.specup.dto.FaqDto;
import com.supring.specup.dto.FaqRequest;
import com.supring.specup.service.CsService;
import com.supring.specup.service.FaqService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class CsController {
    private static final Logger log = LoggerFactory.getLogger(CsController.class);

    private final CsService csService;
    private final FaqService faqService;

    // --- FAQ Endpoints ---

    @Operation(summary = "FAQ 목록 조회")
    @GetMapping("/faq")
    public ResponseEntity<List<FaqDto>> listFaqs() {
        return ResponseEntity.ok(faqService.findAll());
    }

    @Operation(summary = "FAQ 상세 조회")
    @GetMapping("/faq/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FaqDto> getFaq(@PathVariable Long id) {
        return ResponseEntity.ok(faqService.findById(id));
    }

    @Operation(summary = "FAQ 등록")
    @PostMapping("/faq")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FaqDto> createFaq(@RequestBody FaqRequest req) {
        FaqDto created = faqService.create(req.getQuestion(), req.getAnswer());
        URI location = URI.create("/api/support/faq/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "FAQ 수정")
    @PutMapping("/faq/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateFaq(
            @PathVariable Long id,
            @RequestBody FaqRequest req) {
        faqService.update(id, req.getQuestion(), req.getAnswer());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "FAQ 삭제")
    @DeleteMapping("/faq/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        faqService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- CS (1:1 문의) Endpoints ---

    @Operation(summary = "내 문의 목록 조회")
    @GetMapping("/inquiry/my")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<CsDto>> listMyInquiries(Authentication auth) {
        String memberId = auth.getName();
        return ResponseEntity.ok(csService.listByOwner(memberId));
    }

    @Operation(summary = "문의 등록")
    @PostMapping("/inquiry")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(CsDto.Detail.class)
    public ResponseEntity<CsDto> createInquiry(
            @RequestBody CsRequest req,
            Authentication auth) {
        log.debug("▶▶▶ title = {}", req.getTitle());
        log.debug("▶▶▶ content = {}", req.getContent());
        log.debug("▶▶▶ photo = {}", req.getPhoto());

        CsDto created = csService.create(auth.getName(), req);
        URI location = URI.create("/api/support/inquiry/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "문의 상세 조회")
    @GetMapping("/inquiry/{csId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(CsDto.Detail.class)
    public ResponseEntity<CsDto> getInquiry(
            @PathVariable Long csId,
            Authentication auth) {
        return ResponseEntity.ok(csService.getIfOwner(auth.getName(), csId));
    }

    @Operation(summary = "문의 수정")
    @PutMapping("/inquiry/{csId}")
    @PreAuthorize("@csSecurity.isOwner(authentication.name, #csId) or hasRole('ADMIN')")
    public ResponseEntity<Void> updateInquiry(
            @PathVariable Long csId,
            @RequestBody CsRequest req) {
        csService.update(csId, req);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "문의 삭제")
    @DeleteMapping("/inquiry/{csId}")
    @PreAuthorize("@csSecurity.isOwner(authentication.name, #csId) or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long csId) {
        csService.delete(csId);
        return ResponseEntity.noContent().build();
    }

    // --- 관리자 답변 Endpoints ---

    @Operation(summary = "답변 등록")
    @PostMapping("/inquiry/admin/answer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CsDto> createAnswer(
            @RequestParam Long csId,
            @RequestParam String answer,
            Authentication auth) {
        CsDto dto = csService.createAdminAnswer(csId, answer, auth.getName());
        URI location = URI.create("/api/support/inquiry/" + csId);
        return ResponseEntity.created(location).body(dto);
    }

    @Operation(summary = "답변 수정")
    @PutMapping("/inquiry/admin/answer/{csId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CsDto> updateAnswer(
            @PathVariable Long csId,
            @RequestParam String answer,
            Authentication auth) {
        return ResponseEntity.ok(csService.updateAdminAnswer(csId, answer, auth.getName()));
    }

    @Operation(summary = "답변 삭제")
    @DeleteMapping("/inquiry/admin/answer/{csId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long csId,
            Authentication auth) {
        csService.deleteAdminAnswer(csId, auth.getName());
        return ResponseEntity.noContent().build();
    }

    // --- 관리자 조회 Endpoints ---

    @Operation(summary = "모든 1:1 문의 목록 조회 (관리자 전용)")
    @GetMapping("/inquiry")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CsDto>> listAllInquiries() {
        return ResponseEntity.ok(csService.listAll());
    }

    @Operation(summary = "특정 사용자 1:1 문의 조회 (관리자 전용)")
    @GetMapping("/inquiry/user/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CsDto>> listUserInquiries(@PathVariable String memberId) {
        return ResponseEntity.ok(csService.listByOwnerForAdmin(memberId));
    }
}
