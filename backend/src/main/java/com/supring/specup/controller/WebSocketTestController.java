package com.supring.specup.controller;

import com.supring.specup.model.ChatMessage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class WebSocketTestController {

    private final SimpMessagingTemplate messagingTemplate;

    @Operation(summary = "웹소켓 테스트 메시지 전송")
    @PostMapping("/send-message")
    public ResponseEntity<String> sendTestMessage(@RequestBody ChatMessage message) {
        message.setTimestamp(LocalDateTime.now().toString());
        messagingTemplate.convertAndSend("/topic/public", message);
        return ResponseEntity.ok("Message sent"); // 200 OK
    }
}
