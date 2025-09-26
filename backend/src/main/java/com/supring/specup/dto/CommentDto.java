package com.supring.specup.dto;

import com.supring.specup.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDto {

    private Long commentId;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;

    public static CommentDto of(Comment comment) {
        return CommentDto.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .authorName(comment.getUser().getMemberId()) // User 엔티티의 username 필드 사용
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
