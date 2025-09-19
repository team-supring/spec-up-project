package com.supring.specup.dto;

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

    private Long id;
    private String ownerName;
    private String title;
    private String content;
    private List<String> photo;
    private String csAnswer;
    private String csAnswerYN;
    private String answeredBy;
    private LocalDateTime createdAt;
    private LocalDateTime repliedAt;

    public static CsDto of(Cs cs) {
        List<String> photoList;
        try {
            photoList = new ObjectMapper()
                    .readValue(cs.getPhoto(), new TypeReference<List<String>>() {
                    });
        } catch (Exception e) {
            photoList = List.of();
        }
        return CsDto.builder()
                .id(cs.getId())
                .ownerName(cs.getOwner().getName())
                .title(cs.getTitle())
                .content(cs.getContent())
                .photo(photoList)
                .csAnswer(cs.getCsAnswer())
                .csAnswerYN(cs.getCsAnswerYN())
                .answeredBy(cs.getAnsweredBy())
                .createdAt(cs.getCreatedAt())
                .repliedAt(cs.getRepliedAt())
                .build();
    }
}
