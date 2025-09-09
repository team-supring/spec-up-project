package com.supring.specup.controller;

import com.supring.specup.dto.CsDto;
import com.supring.specup.dto.CsRequest;
import com.supring.specup.dto.FaqDto;
import com.supring.specup.dto.FaqRequest;
import com.supring.specup.service.CsService;
import com.supring.specup.service.FaqService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cs")
@RequiredArgsConstructor
public class CsController {

    private final CsService csService;
    private final FaqService faqService;

    // --- CS 게시판 ---

    @Operation(summary = "CS 글 상세 조회")
    @GetMapping("/{csId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CsDto> get(@PathVariable Long csId) {
        return ResponseEntity.ok(csService.get(csId));
    }

    @Operation(summary = "CS 글 목록 조회")
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<CsDto>> list(@RequestParam(required = false) Boolean mine, Authentication auth) {
        if (Boolean.TRUE.equals(mine)) {
            return ResponseEntity.ok(csService.listByOwner(auth.getName()));
        }
        return ResponseEntity.ok(csService.listAll());
    }

    @Operation(summary = "CS 글 작성")
    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CsDto> create(@RequestBody CsRequest req, Authentication auth) {
        CsDto created = csService.create(auth.getName(), req);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "CS 글 수정")
    @PutMapping("/{csId}")
    @PreAuthorize("@csSecurity.isOwner(authentication.name, #csId) or hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable Long csId,
            @RequestBody CsRequest req) {
        csService.update(csId, req);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "CS 글 삭제")
    @DeleteMapping("/{csId}")
    @PreAuthorize("@csSecurity.isOwner(authentication.name, #csId) or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long csId) {
        csService.delete(csId);
        return ResponseEntity.noContent().build();
    }

}
