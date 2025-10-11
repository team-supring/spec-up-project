package com.supring.specup.service;

import com.supring.specup.domain.Comment;
import com.supring.specup.domain.CommunityPost;
import com.supring.specup.domain.User;
import com.supring.specup.dto.CommentDto;
import com.supring.specup.dto.CommentRequest;
import com.supring.specup.dto.CommunityPostDto;
import com.supring.specup.dto.CommunityPostRequest;
import com.supring.specup.repository.CommentRepository;
import com.supring.specup.repository.CommunityPostRepository;
import com.supring.specup.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityBoardServiceImpl implements CommunityBoardService {

    private final CommunityPostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public Page<CommunityPostDto> getPostList(Pageable pageable) {
        return postRepository
                .findAllByOrderByCreatedAtDesc(pageable)
                .map(post -> {
                    long cnt = commentRepository.countByPost_PostId(post.getPostId());
                    return CommunityPostDto.summaryWithCommentCount(post, cnt);
                });
    }

    @Override
    public CommunityPostDto getPostDetail(Long postId) {
        CommunityPost post = postRepository.findByIdWithUser(postId);
        if (post == null)
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        List<Comment> comments = commentRepository.findByPostIdWithUser(postId);
        post.setComments(comments);
        return CommunityPostDto.detail(post);
    }

    @Override
    @Transactional
    public CommunityPostDto createPost(CommunityPostRequest request, String memberId) {
        User user = userRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        CommunityPost post = CommunityPost.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .likes(0)
                .build();
        CommunityPost saved = postRepository.save(post);
        return CommunityPostDto.summary(saved);
    }

    @Override
    @Transactional
    public CommentDto createComment(Long postId, CommentRequest request, String memberId) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        User user = userRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(request.getContent())
                .build();
        Comment saved = commentRepository.save(comment);
        return CommentDto.of(saved);
    }

    @Override
    public List<CommentDto> getCommentList(Long postId) {
        return commentRepository.findByPostIdWithUser(postId)
                .stream().map(CommentDto::of).collect(Collectors.toList());
    }

    // 게시글 수정
    @Transactional
    @Override
    public CommunityPostDto updatePost(Long postId, CommunityPostRequest request, String username) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. id=" + postId));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        return CommunityPostDto.detail(post);
    }

    // 게시글 삭제
    @Transactional
    @Override
    public void deletePost(Long postId) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. id=" + postId));

        postRepository.delete(post);
    }

    // 댓글 수정
    @Transactional
    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentRequest request, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다. id=" + commentId));

        // 댓글이 해당 게시글에 속하는지 확인
        if (!comment.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("댓글이 해당 게시글에 속하지 않습니다.");
        }

        comment.setContent(request.getContent());

        return CommentDto.of(comment);
    }

    // 댓글 삭제
    @Transactional
    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다. id=" + commentId));

        // 댓글이 해당 게시글에 속하는지 확인
        if (!comment.getPost().getPostId().equals(postId)) {
            throw new IllegalArgumentException("댓글이 해당 게시글에 속하지 않습니다.");
        }

        commentRepository.delete(comment);
    }

    // 좋아요 기능 구현
    @Transactional
    @Override
    public CommunityPostDto likePost(Long postId, String username) {
        CommunityPost post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. id=" + postId));

        // 단순 증가 버전
        post.setLikes(post.getLikes() + 1);

        // 댓글 수도 함께 반환
        long commentCount = commentRepository.countByPost_PostId(postId);
        return CommunityPostDto.summaryWithCommentCount(post, commentCount);
    }
}
