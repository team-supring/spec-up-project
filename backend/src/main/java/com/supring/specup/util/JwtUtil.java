package com.supring.specup.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${jwt.expiration-ms.access}")
    private long accessExpirationMs;

    @Value("${jwt.expiration-ms.refresh}")
    private long refreshExpirationMs;

    private Key secretKey;

    /** 토큰 만료일 반환 */
    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    /** 토큰에서 Claims 파싱 */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 1번에서 호출할 메서드
    public Long getUserIdFromToken(String token) {
        Claims claims = parseClaims(token);
        Object idClaim = claims.get("userId");
        if (idClaim instanceof Integer) {
            return ((Integer) idClaim).longValue();
        } else if (idClaim instanceof Long) {
            return (Long) idClaim;
        } else {
            throw new IllegalArgumentException("Invalid userId in token");
        }
    }

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    public String generateAccessToken(String memberId, List<String> roles) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(memberId)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(String memberId) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractMemberId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("roles", List.class);
    }

    /** 쿠키 max-age 설정용 밀리초 반환 */
    public long getRefreshExpiry() {
        return refreshExpirationMs;
    }
}
