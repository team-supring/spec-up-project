package com.supring.specup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supring.specup.dto.FaqDto;
import com.supring.specup.dto.FaqRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SupportApiE2ETest {

        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper mapper;

        @Test
        @WithMockUser(roles = "ADMIN")
        void faq_CRUD_Flow() throws Exception {
                // Create
                FaqRequest createReq = new FaqRequest();
                createReq.setQuestion("Q");
                createReq.setAnswer("A");
                String body = mapper.writeValueAsString(createReq);

                MvcResult createResult = mockMvc.perform(post("/api/support/faq")
                                .with(csrf())
                                .contentType("application/json")
                                .content(body))
                                .andExpect(status().isCreated())
                                .andReturn();

                // Extract ID from response body
                String responseBody = createResult.getResponse().getContentAsString();
                FaqDto created = mapper.readValue(responseBody, FaqDto.class);
                Long faqId = created.getId();

                // Read
                mockMvc.perform(get("/api/support/faq/" + faqId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.question").value("Q"));

                // Update
                createReq.setQuestion("Q2");
                createReq.setAnswer("A2");
                mockMvc.perform(put("/api/support/faq/" + faqId)
                                .with(csrf())
                                .contentType("application/json")
                                .content(mapper.writeValueAsString(createReq)))
                                .andExpect(status().isNoContent());

                // Delete
                mockMvc.perform(delete("/api/support/faq/" + faqId)
                                .with(csrf()))
                                .andExpect(status().isNoContent());
        }
}
