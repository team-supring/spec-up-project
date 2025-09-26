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
                .map(CommunityPostDto::summary);
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
}
