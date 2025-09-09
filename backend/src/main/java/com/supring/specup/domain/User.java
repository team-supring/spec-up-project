package com.supring.specup.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    // 로그인 아이디
    @Column(name = "member_id", length = 50, nullable = false, unique = true)
    private String memberId;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    // 회원명
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "birth", length = 10)
    private String birth;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "profile_image", length = 255)
    private String profileImage;

    @Column(name = "profile_url", length = 255)
    private String profileUrl;

    @Column(name = "trade_point")
    private Double tradePoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private Role role;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 엔티티에 매핑된 Role 열거형을 String 리스트로 반환
     */
    public List<String> getRoleList() {
        return List.of(role.name());
    }
}
