package com.supring.specup.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String writer; // username
    private LocalDateTime createdAt;
}
