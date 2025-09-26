package com.supring.specup.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CsInquiryDto {
    private Long csId;
    private String csTitle;
    private String title;
    private String content;
    private String time;
    private String csAnswerYN;
}
