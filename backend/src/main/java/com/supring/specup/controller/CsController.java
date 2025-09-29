package com.supring.specup.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.supring.specup.dto.CsDto;
import com.supring.specup.dto.CsRequest;
import com.supring.specup.service.CsService;
import com.supring.specup.util.JwtUtil;
import com.supring.specup.domain.User;
import com.supring.specup.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.net.URI;

@RestController
@RequestMapping("/api/support/cs")
@RequiredArgsConstructor
public class CsController {
    private static final Logger log = LoggerFactory.getLogger(CsController.class);
    private final CsService csService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Operation(summary = "1:1 문의 목록 조회")
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(CsDto.Summary.class)
    public ResponseEntity<Page<CsDto>> listInquiries(Pageable pageable,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestHeader("Authorization") String authHeader,
            Authentication auth) {

        boolean isAdmin = auth.getAuthorities().stream()
                // .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        String token = authHeader.replace("Bearer ", "");
        String memberId = jwtUtil.extractMemberId(token);

        PageRequest pr = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<CsDto> result;

        if (isAdmin) {
            result = csService.listAll(pr);
        } else {
            User user = userRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new UsernameNotFoundException(memberId));
            // result = csService.listByOwner(user.getUserId(), pr);
            result = csService.listByOwner(user.getUserId(), pr);
            System.err.println("result : " + result);
        }

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "1:1 문의 상세 조회")
    @GetMapping("/{csId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(CsDto.Detail.class)
    public ResponseEntity<CsDto> getInquiry(
            @PathVariable Long csId,
            Authentication auth) {
        String memberId = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        CsDto dto = isAdmin
                ? csService.getById(csId)
                : csService.getIfOwner(memberId, csId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "문의 등록")
    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @JsonView(CsDto.Detail.class)
    public ResponseEntity<CsDto> createInquiry(
            @RequestBody CsRequest req,
            Authentication auth) {
        log.debug("▶▶▶ title = {}", req.getTitle());
        log.debug("▶▶▶ content = {}", req.getContent());
        log.debug("▶▶▶ photo = {}", req.getPhoto());

        CsDto created = csService.create(auth.getName(), req);
        URI location = URI.create("/api/support/cs/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "문의 수정")
    @PutMapping("/{csId}")
    @PreAuthorize("@csSecurity.isOwner(authentication.name, #csId) or hasRole('ADMIN')")
    public ResponseEntity<Void> updateInquiry(
            @PathVariable Long csId,
            @RequestBody CsRequest req) {
        csService.update(csId, req);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "문의 삭제")
    @DeleteMapping("/{csId}")
    @PreAuthorize("@csSecurity.isOwner(authentication.name, #csId) or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long csId) {
        csService.delete(csId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "답변 등록")
    @PostMapping("/{csId}/answer")
    @PreAuthorize("hasRole('ADMIN')")
    @JsonView(CsDto.Detail.class)
    public ResponseEntity<CsDto> createAnswer(
            @PathVariable Long csId,
            @RequestBody String answer,
            Authentication auth) {
        CsDto dto = csService.createAdminAnswer(csId, answer, auth.getName());
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "답변 수정")
    @PutMapping("/{csId}/answer")
    @PreAuthorize("hasRole('ADMIN')")
    @JsonView(CsDto.Detail.class)
    public ResponseEntity<CsDto> updateAnswer(
            @PathVariable Long csId,
            @RequestBody String answer,
            Authentication auth) {
        CsDto dto = csService.updateAdminAnswer(csId, answer, auth.getName());
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "답변 삭제")
    @DeleteMapping("/answer/{csId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long csId,
            Authentication auth) {
        csService.deleteAdminAnswer(csId, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
