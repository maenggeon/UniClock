package com.uniclock.backend.common.security;

import com.uniclock.backend.domain.user.data.response.JwtUserDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenExpTime;

    public JwtTokenProvider(
            @Value("${jwt.secret}") final String secretKey,
            @Value("${jwt.expiration_time}") final long accessTokenExpTime)
    {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
    }

    /**
     * Acess Token 생성
     */
    public String createAccessToken(JwtUserDTO userDTO) {
        return createToken(userDTO, accessTokenExpTime);
    }

    /**
     * JWT 생성
     */
    private String createToken(JwtUserDTO dto, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("userId", dto.getUserId());
        claims.put("loginId", dto.getLoginId());
        claims.put("email", dto.getEmail());
        claims.put("name", dto.getName());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Token에서 User Id 추출
     */
    public Long getUserId(String token) {
        return parseClaims(token).get("userId", Long.class);
    }

    /**
     * JWT 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * JWT Claims 추출
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

        } catch(ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}