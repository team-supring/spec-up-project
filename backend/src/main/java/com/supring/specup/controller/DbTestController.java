package com.supring.specup.controller;

import com.supring.specup.repository.FaqRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DbTestController {

    private final FaqRepository faqRepository;

    @Operation(summary = "DB 연결 테스트 및 FAQ 카운트 조회")
    @GetMapping("/db-test")
    public ResponseEntity<Map<String, Object>> dbTest() {
        long count = faqRepository.count(); // FAQ 테이블 레코드 수 조회
        Map<String, Object> result = Map.of(
                "dbConnected", true,
                "faqCount", count);
        return ResponseEntity.ok(result); // 200 OK
    }
}
