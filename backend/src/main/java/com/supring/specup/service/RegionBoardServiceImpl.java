package com.supring.specup.service;

import com.supring.specup.domain.RegionPost;
import com.supring.specup.dto.PostDto;
import com.supring.specup.dto.PostRequest;
import com.supring.specup.repository.RegionPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionBoardServiceImpl implements RegionBoardService {

    private final RegionPostRepository postRepository;

    /**
     * 전체 지역 게시판 글 목록 조회.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PostDto> listAll() {
        return postRepository.findAll().stream()
                .map(PostDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 단일 글 상세 조회.
     */
    @Override
    @Transactional(readOnly = true)
    public PostDto get(Long postId) {
        RegionPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("글을 찾을 수 없습니다."));
        return PostDto.of(post);
    }

    /**
     * 글 작성: 로그인 사용자(username) 필드에 저장.
     */
    @Override
    @Transactional
    public PostDto create(String username, PostRequest request) {
        RegionPost post = RegionPost.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(username)
                .createdAt(LocalDateTime.now())
                .build();
        RegionPost saved = postRepository.save(post);
        return PostDto.of(saved);
    }

    /**
     * 글 수정: 기존 글 불러와 제목·내용 변경.
     */
    @Override
    @Transactional
    public void update(Long postId, PostRequest request) {
        RegionPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("글을 찾을 수 없습니다."));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        postRepository.save(post);
    }

    /**
     * 글 삭제: ID 기반 삭제.
     */
    @Override
    @Transactional
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}
