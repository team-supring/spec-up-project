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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "cs_title", nullable = false)
    private String title;

    @Column(name = "cs_description", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "JSON")
    private String photo;

    @Column(name = "cs_answer")
    private String csAnswer;

    @Column(name = "cs_answer_yn", length = 1)
    private String csAnswerYN;

    @Column(name = "answered_by")
    private String answeredBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "replied_at")
    private LocalDateTime repliedAt;
}
