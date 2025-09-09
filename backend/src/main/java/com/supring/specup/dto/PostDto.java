package com.supring.specup.dto;

import com.supring.specup.domain.RegionPost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    // 엔티티 → DTO 변환
    public static PostDto of(RegionPost post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
