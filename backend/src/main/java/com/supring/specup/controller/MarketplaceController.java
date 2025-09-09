package com.supring.specup.controller;

import com.supring.specup.dto.ItemDto;
import com.supring.specup.dto.ItemRequest;
import com.supring.specup.service.MarketplaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marketplace")
@RequiredArgsConstructor
public class MarketplaceController {

    private final MarketplaceService service;

    @Operation(summary = "중고거래 게시판 전체 목록 조회")
    @GetMapping
    public ResponseEntity<List<ItemDto>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "중고거래 게시판 글 상세 조회")
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> get(@PathVariable Long itemId) {
        return ResponseEntity.ok(service.get(itemId));
    }

    @Operation(summary = "중고거래 게시판 글 작성")
    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ItemDto> create(@RequestBody ItemRequest req,
            Authentication auth) {
        ItemDto created = service.create(auth.getName(), req);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "중고거래 게시판 글 수정")
    @PutMapping("/{itemId}")
    @PreAuthorize("@marketplaceSecurity.isOwner(authentication.name, #itemId) or hasRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable Long itemId,
            @RequestBody ItemRequest req) {
        service.update(itemId, req);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "중고거래 게시판 글 삭제")
    @DeleteMapping("/{itemId}")
    @PreAuthorize("@marketplaceSecurity.isOwner(authentication.name, #itemId) or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long itemId) {
        service.delete(itemId);
        return ResponseEntity.noContent().build();
    }
}
