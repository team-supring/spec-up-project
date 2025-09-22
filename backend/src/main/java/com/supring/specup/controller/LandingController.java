package com.supring.specup.controller;

import com.supring.specup.dto.LandingResponse;
import com.supring.specup.service.LandingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/support")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LandingController {

        private final LandingService landingService;

        @GetMapping("/landing")
        public ResponseEntity<LandingResponse> getLanding() {
                LandingResponse response = landingService.getLandingData();
                return ResponseEntity.ok(response);
        }
}
