package com.supring.specup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {
    private String title;
    private String description;
    private Integer price;
}
