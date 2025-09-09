package com.supring.specup.dto;

import com.supring.specup.domain.Faq;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FaqDto {
    private Long id;
    private String question;
    private String answer;
    private LocalDateTime createdAt;
}