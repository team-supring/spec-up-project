package com.supring.specup.controller;

import com.supring.specup.dto.PostDto;
import com.supring.specup.dto.PostRequest;
import com.supring.specup.service.RegionBoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/region")
@RequiredArgsConstructor
public class RegionBoardController {

    private final RegionBoardService service;

    @Operation(summary = "지역 게시판 전체 목록 조회")
    @GetMapping
    public ResponseEntity<List<PostDto>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "지역 게시판 글 상세 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> get(@PathVariable Long postId) {
        return ResponseEntity.ok(service.get(postId));
    }

    @Operation(summary = "지역 게시판 글 작성")
    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PostDto> create(@RequestBody PostRequest req,
            Authentication auth) {
        PostDto created = service.create(auth.getName(), req);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "지역 게시판 글 수정")
    @PutMapping("/{postId}")
    @PreAuthorize("@boardSecurity.isOwner(authentication.name, #postId) or hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable Long postId,
            @RequestBody PostRequest req) {
        service.update(postId, req);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "지역 게시판 글 삭제")
    @DeleteMapping("/{postId}")
    @PreAuthorize("@boardSecurity.isOwner(authentication.name, #postId) or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long postId) {
        service.delete(postId);
        return ResponseEntity.noContent().build();
    }
}
