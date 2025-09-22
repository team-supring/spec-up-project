package com.supring.specup.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supring.specup.domain.Cs;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CsDto {

    public interface Summary {
    }

    public interface Detail extends Summary {
    }

    @JsonView(Summary.class)
    private Long id;

    @JsonView(Summary.class)
    private String ownerName;

    @JsonView(Summary.class)
    private String title;

    @JsonView(Summary.class)
    private String content;

    @JsonView(Detail.class)
    private String csAnswer;

    @JsonView(Detail.class)
    private String csAnswerYN;

    @JsonView(Detail.class)
    private String answeredBy;

    @JsonView(Summary.class)
    private LocalDateTime createdAt;

    @JsonView(Detail.class)
    private LocalDateTime repliedAt;

    public static CsDto of(Cs cs) {
        return CsDto.builder()
                .id(cs.getId())
                .ownerName(cs.getOwner().getName())
                .title(cs.getTitle())
                .content(cs.getContent())
                .csAnswer(cs.getCsAnswer())
                .csAnswerYN(cs.getCsAnswerYN())
                .answeredBy(cs.getAnsweredBy())
                .createdAt(cs.getCreatedAt())
                .repliedAt(cs.getRepliedAt())
                .build();
    }
}
