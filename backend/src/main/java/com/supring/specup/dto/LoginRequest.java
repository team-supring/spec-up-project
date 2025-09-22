package com.supring.specup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LoginRequest {
    @JsonProperty("memberId")
    private String memberId;

    @JsonProperty("memberPassword")
    private String memberPassword;
}
