package com.supring.specup.service;

import com.supring.specup.domain.Faq;
import com.supring.specup.dto.FaqDto;
import com.supring.specup.repository.FaqRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FaqServiceImplTest {

    @Mock
    private FaqRepository faqRepository;

    @InjectMocks
    private FaqServiceImpl faqService;

    @Test
    void findAll_ReturnsInDescendingOrder() {
        LocalDateTime now = LocalDateTime.now();
        Faq older = new Faq(1L, "Q1", "A1", now.minusDays(1));
        Faq newer = new Faq(2L, "Q2", "A2", now);
        when(faqRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(List.of(newer, older));

        List<FaqDto> result = faqService.findAll();

        assertThat(result).extracting(FaqDto::getId)
                .containsExactly(2L, 1L);
    }

    @Test
    void findById_NotFound_Throws404() {
        when(faqRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> faqService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404");
    }
}
