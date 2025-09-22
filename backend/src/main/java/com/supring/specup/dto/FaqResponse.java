package com.supring.specup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class FaqResponse {
    private Long faqId;
    private String question;
    private String answer;
}
