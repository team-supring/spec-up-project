package com.supring.specup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SignupRequest {
    private String name;

    @JsonProperty("memberId")
    private String memberId;

    @JsonProperty("memberPassword")
    private String memberPassword;

    private String email;
    private String birth;
    private Integer sex;

    // 추가: 지역 ID
    private Long regionId;
}
