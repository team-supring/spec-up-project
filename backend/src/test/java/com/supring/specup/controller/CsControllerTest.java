package com.supring.specup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supring.specup.dto.CsDto;
import com.supring.specup.dto.CsRequest;
import com.supring.specup.dto.FaqDto;
import com.supring.specup.repository.UserRepository;
import com.supring.specup.service.CsService;
import com.supring.specup.service.FaqService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@WebMvcTest(CsController.class)
class CsControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private FaqService faqService;
        @MockBean
        private CsService csService;

        @Autowired
        private ObjectMapper mapper;
        @Mock
        private UserRepository userRepository; // 추가

        @Test
        @WithMockUser // <— 추가
        void listFaqs_ReturnsOk() throws Exception {
                when(faqService.findAll())
                                .thenReturn(List.of(new FaqDto(1L, "Q", "A", LocalDateTime.now())));
                mockMvc.perform(get("/api/support/faq"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].question").value("Q"));
        }

        @Test
        @WithMockUser(roles = "USER", username = "u1")
        void createInquiry_ReturnsCreated() throws Exception {
                CsRequest req = new CsRequest();
                req.setTitle("t");
                req.setContent("c");
                req.setPhoto(List.of("url1"));

                CsDto dto = CsDto.builder()
                                .id(1L)
                                .ownerName("u1") // writer → ownerName
                                .title("t")
                                .content("c")
                                .photo(List.of("url1"))
                                .csAnswer(null)
                                .csAnswerYN("N")
                                .answeredBy(null)
                                .createdAt(LocalDateTime.now())
                                .repliedAt(null)
                                .build();

                when(csService.create(eq("u1"), any(CsRequest.class)))
                                .thenReturn(dto);

                mockMvc.perform(post("/api/support/inquiry")
                                .with(csrf())
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(req)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1));
        }
}
