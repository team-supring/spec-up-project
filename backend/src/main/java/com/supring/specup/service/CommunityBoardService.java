package com.supring.specup.service;

import com.supring.specup.dto.CommentDto;
import com.supring.specup.dto.CommentRequest;
import com.supring.specup.dto.CommunityPostDto;
import com.supring.specup.dto.CommunityPostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommunityBoardService {

    // 게시글 목록 조회 (페이징)
    Page<CommunityPostDto> getPostList(Pageable pageable);

    // 게시글 상세 조회 (댓글 포함)
    CommunityPostDto getPostDetail(Long postId);

    // 게시글 작성
    CommunityPostDto createPost(CommunityPostRequest request, String username);

    // 게시글 수정 - 추가
    CommunityPostDto updatePost(Long postId, CommunityPostRequest request, String username);

    // 게시글 삭제 - 추가
    void deletePost(Long postId);

    // 댓글 작성
    CommentDto createComment(Long postId, CommentRequest request, String username);

    // 댓글 수정 - 추가
    CommentDto updateComment(Long postId, Long commentId, CommentRequest request, String username);

    // 댓글 삭제 - 추가
    void deleteComment(Long postId, Long commentId);

    // 특정 게시글의 댓글 목록 조회
    List<CommentDto> getCommentList(Long postId);

    // 좋아요 기능 추가
    CommunityPostDto likePost(Long postId, String username);
}
