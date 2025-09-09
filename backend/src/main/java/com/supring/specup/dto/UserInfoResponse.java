package com.supring.specup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private String name;
    private String profile;
    private double tradePoint;
    private String region;
}
