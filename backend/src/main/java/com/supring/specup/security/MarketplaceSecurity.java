package com.supring.specup.security;

import com.supring.specup.domain.Product;
import com.supring.specup.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarketplaceSecurity {

    private final ProductRepository productRepository;

    public boolean isOwner(String username, Long itemId) {
        return productRepository.findById(itemId)
                .map(p -> p.getSeller().getName().equals(username))
                .orElse(false);
    }
}
