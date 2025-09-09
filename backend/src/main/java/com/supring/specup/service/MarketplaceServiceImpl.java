package com.supring.specup.service;

import com.supring.specup.domain.Product;
import com.supring.specup.dto.ItemDto;
import com.supring.specup.dto.ItemRequest;
import com.supring.specup.repository.ProductRepository;
import com.supring.specup.repository.UserRepository;
import com.supring.specup.domain.User;
import com.supring.specup.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketplaceServiceImpl implements MarketplaceService {

    private final ProductRepository itemRepository;
    private final UserRepository userRepository;

    /**
     * 전체 상품 목록 조회.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> listAll() {
        return itemRepository.findAll().stream()
                .map(ItemDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 단일 상품 상세 조회.
     */
    @Override
    @Transactional(readOnly = true)
    public ItemDto get(Long itemId) {
        Product item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        return ItemDto.of(item);
    }

    /**
     * 상품 등록: 판매자(memberId) 저장.
     */
    @Override
    @Transactional
    public ItemDto create(String memberId, ItemRequest req) {
        User user = userRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        Product product = Product.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .price(req.getPrice())
                .seller(user)
                .createdAt(LocalDateTime.now())
                .build();

        Product saved = itemRepository.save(product);
        return ItemDto.of(saved);
    }

    /**
     * 상품 수정: 제목·설명·가격 업데이트.
     */
    @Override
    @Transactional
    public void update(Long itemId, ItemRequest request) {
        Product item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        itemRepository.save(item);
    }

    /**
     * 상품 삭제: ID 기반 삭제.
     */
    @Override
    @Transactional
    public void delete(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
