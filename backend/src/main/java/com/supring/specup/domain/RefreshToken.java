package com.supring.specup.domain;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String memberId;
    private LocalDateTime createdAt;

    public void updateToken(String newToken) {
        this.token = newToken;
        this.createdAt = LocalDateTime.now();
    }
}
