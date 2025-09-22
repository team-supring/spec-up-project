package com.supring.specup.service;

import com.supring.specup.dto.LandingResponse;
import com.supring.specup.domain.Cs;
import com.supring.specup.repository.CsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LandingServiceImpl implements LandingService {

    private final CsRepository csRepository;

    @Override
    public LandingResponse getLandingData() {
        List<LandingResponse.TagInfo> tagList = Arrays.asList(
            LandingResponse.TagInfo.builder()
                .tagId(1L)
                .question("자주 묻는 질문")
                .answer("FAQ를 확인해보세요")
                .build(),
            LandingResponse.TagInfo.builder()
                .tagId(2L)
                .question("계정 관련")
                .answer("로그인/회원가입 문의")
                .build(),
            LandingResponse.TagInfo.builder()
                .tagId(3L)
                .question("결제 관련")
                .answer("결제 및 환불 문의")
                .build(),
            LandingResponse.TagInfo.builder()
                .tagId(4L)
                .question("기타 문의")
                .answer("기타 궁금한 사항")
                .build()
        );

        List<Cs> recentCs = csRepository.findAll(
            PageRequest.of(0, 4, Sort.by("createdAt").descending())
        ).getContent();

        List<LandingResponse.CsInfo> csList = recentCs.stream()
            .map((Cs cs) -> LandingResponse.CsInfo.builder()
                .csId(cs.getId())
                .csTitle(cs.getTitle())
                .time(cs.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .csAnswerYN(cs.getCsAnswerYN())
                .build())
            .collect(Collectors.toList());

        return LandingResponse.builder()
            .tagList(tagList)
            .csList(csList)
            .build();
    }
}
