package com.supring.specup.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CsResponseDto {
    private Long csId;
    private String csAnswer;
    private String csAnswerYN;
    private String answeredBy;
}
