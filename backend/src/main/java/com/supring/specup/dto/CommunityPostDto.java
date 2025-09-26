package com.supring.specup.dto;

import com.supring.specup.domain.CommunityPost;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CommunityPostDto {

    public interface Summary {
    }

    public interface Detail extends Summary {
    }

    private Long postId;

    private String title;

    private String authorName;

    private int likes;

    private LocalDateTime createdAt;

    // 상세 뷰에서 노출할 필드

    private String content;

    private List<CommentDto> comments;

    // Summary용 변환 (목록 조회)
    public static CommunityPostDto summary(CommunityPost post) {
        return CommunityPostDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .authorName(post.getUser().getMemberId()) // User 엔티티의 username 필드 사용
                .likes(post.getLikes())
                .createdAt(post.getCreatedAt())
                .build();
    }

    // Detail용 변환 (상세 조회)
    public static CommunityPostDto detail(CommunityPost post) {
        return CommunityPostDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorName(post.getUser().getMemberId()) // User 엔티티의 username 필드 사용
                .likes(post.getLikes())
                .createdAt(post.getCreatedAt())
                .comments(post.getComments() != null ? post.getComments().stream()
                        .map(CommentDto::of)
                        .collect(Collectors.toList()) : null)
                .build();
    }
}
