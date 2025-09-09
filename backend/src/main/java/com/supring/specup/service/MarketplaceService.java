package com.supring.specup.service;

import com.supring.specup.dto.ItemDto;
import com.supring.specup.dto.ItemRequest;

import java.util.List;

public interface MarketplaceService {
    List<ItemDto> listAll();

    ItemDto get(Long itemId);

    ItemDto create(String username, ItemRequest request);

    void update(Long itemId, ItemRequest request);

    void delete(Long itemId); // ← 이 메서드가 반드시 선언돼 있어야 합니다.
}
