package com.supring.specup.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CsRequest {

    private Long ownerId;
    private String title;
    private String content;
    private List<String> photo;

}
