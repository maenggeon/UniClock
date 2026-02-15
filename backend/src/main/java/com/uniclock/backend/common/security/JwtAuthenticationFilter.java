package com.uniclock.backend.common.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 화이트리스트 경로
     */
    private static final String[] SHOULD_NOT_FILTER_URLS = {
            "/api/auth/login", "/api/auth/signup"
    };

    /**
     * JWT 검증 필터
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Request Header에서 JWT 토큰 추출
        String token = resolveToken(request);
        String requestURI = request.getRequestURI();

        // 토큰 유효성 검증
        try {
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다. URI: {}", authentication.getName(), requestURI);
            }
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "EXPIRED_TOKEN");
            log.error("Token Expired: {}", e.getMessage());
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            request.setAttribute("exception", "INVALID_TOKEN");
            log.error("Invalid Token: {}", e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            request.setAttribute("exception", "UNKNOWN_ERROR");
            log.error("JWT Filter Error: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 화이트리스트
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(SHOULD_NOT_FILTER_URLS)
                .anyMatch(url -> request.getRequestURI().startsWith(url));
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
