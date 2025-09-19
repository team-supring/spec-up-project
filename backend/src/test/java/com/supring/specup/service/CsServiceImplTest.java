package com.supring.specup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supring.specup.domain.Cs;
import com.supring.specup.dto.CsDto;
import com.supring.specup.dto.CsRequest;
import com.supring.specup.repository.CsRepository;
import com.supring.specup.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.supring.specup.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CsServiceImplTest {

        @Mock
        private CsRepository csRepository;

        @InjectMocks
        private CsServiceImpl csService;
        @Mock
        private UserRepository userRepository;
        private final ObjectMapper mapper = new ObjectMapper();

        @Test
        void create_SerializesPhotoAndReturnsDto() throws Exception {
                User owner = User.builder()
                                .userId(1L)
                                .name("user")
                                .build();
                owner.setUserId(1L);
                owner.setName("user");
                when(userRepository.findByMemberId("user"))
                                .thenReturn(Optional.of(owner));

                CsRequest req = new CsRequest();
                req.setTitle("t");
                req.setContent("c");
                req.setPhoto(List.of("url1"));

                Cs saved = Cs.builder()
                                .id(1L)
                                .owner(owner)
                                .title("t")
                                .content("c")
                                .photo(mapper.writeValueAsString(req.getPhoto()))
                                .csAnswerYN("N")
                                .createdAt(LocalDateTime.now())
                                .build();

                when(csRepository.save(any(Cs.class))).thenReturn(saved);

                CsDto dto = csService.create("user", req);

                assertThat(dto.getId()).isEqualTo(1L);
                assertThat(dto.getPhoto()).containsExactly("url1");
        }

        @Test
        void getIfOwner_NotOwner_Throws() {
                User owner = User.builder()
                                .userId(1L)
                                .name("user")
                                .build();
                when(csRepository.findById(1L))
                                .thenReturn(Optional.of(Cs.builder().owner(owner).build()));

                assertThatThrownBy(() -> csService.getIfOwner("user", 1L))
                                .isInstanceOf(RuntimeException.class);
        }
}
