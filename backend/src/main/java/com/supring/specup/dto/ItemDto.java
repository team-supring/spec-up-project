package com.supring.specup.dto;

import com.supring.specup.domain.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ItemDto {
    private Long id;
    private String title;
    private String description;
    private Integer price;
    private String seller;
    private LocalDateTime createdAt;

    // 엔티티 → DTO 변환
    public static ItemDto of(Product item) {
        return ItemDto.builder()
                .id(item.getProductId())
                .title(item.getTitle())
                .description(item.getDescription())
                .price(item.getPrice())
                .seller(item.getSeller().getMemberId())
                .createdAt(item.getCreatedAt())
                .build();
    }
}
