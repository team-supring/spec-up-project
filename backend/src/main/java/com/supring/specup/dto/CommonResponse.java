package com.supring.specup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse {
    private boolean success;
    private String message;
}
