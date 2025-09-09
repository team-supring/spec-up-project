package com.supring.specup.dto;

import com.supring.specup.domain.Cs;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CsDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;

    public static CsDto of(Cs cs) {
        return CsDto.builder()
                .id(cs.getId())
                .title(cs.getTitle())
                .content(cs.getContent())
                .writer(cs.getWriter())
                .createdAt(cs.getCreatedAt())
                .build();
    }
}
