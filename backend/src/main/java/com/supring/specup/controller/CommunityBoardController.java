package com.supring.specup.controller;

import com.supring.specup.dto.CommentDto;
import com.supring.specup.dto.CommentRequest;
import com.supring.specup.dto.CommunityPostDto;
import com.supring.specup.dto.CommunityPostRequest;
import com.supring.specup.service.CommunityBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
@Tag(name = "Community Board", description = "커뮤니티 게시판 API")
public class CommunityBoardController {

    private final CommunityBoardService communityBoardService;

    @Operation(summary = "게시글 목록 조회", description = "페이징 처리된 게시글 목록을 최신순으로 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<CommunityPostDto>> getPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CommunityPostDto> posts = communityBoardService.getPostList(pageable);
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "게시글 상세 조회", description = "특정 게시글의 상세 정보와 댓글을 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<CommunityPostDto> getPostDetail(@PathVariable Long postId) {
        CommunityPostDto post = communityBoardService.getPostDetail(postId);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다.")
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommunityPostDto> createPost(
            @RequestBody CommunityPostRequest request,
            Authentication auth) {

        String memberId = auth.getName(); // memberId 사용
        CommunityPostDto dto = communityBoardService.createPost(request, memberId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "댓글 작성", description = "특정 게시글에 댓글을 작성합니다.")
    @PostMapping("/{postId}/comments")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request,
            Authentication auth) {

        String memberId = auth.getName(); // memberId 사용
        CommentDto dto = communityBoardService.createComment(postId, request, memberId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "댓글 목록 조회", description = "특정 게시글의 댓글 목록을 조회합니다.")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentList(@PathVariable Long postId) {
        List<CommentDto> comments = communityBoardService.getCommentList(postId);
        return ResponseEntity.ok(comments);
    }
}
