package com.uniclock.backend.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * JWT 검증 필터
     */
    /*
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Request Header에서 JWT 토큰 추출
        String token = resolveToken(request);



        String requestURI = request.getRequestURI();
        log.info(" JWT 필터 실행 - URI: {}", requestURI);
        log.debug("요청 URI: {}", requestURI);
        log.debug("토큰 존재 여부: {}", token != null);


        // 토큰 유효성 검증
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // 토큰에서 Authentication 객체를 가져와 SecurityContext에 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다.", authentication.getName());
        } else {
            log.debug("유효한 JWT 토큰이 없습니다.");
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
    */






    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        log.info("🔍 JWT 필터 실행 - URI: {}", requestURI);

        // Request Header에서 JWT 토큰 추출
        String token = resolveToken(request);

        log.info("🔑 토큰 존재 여부: {}", token != null ? "있음 (길이: " + token.length() + ")" : "없음");

        // 토큰 유효성 검증 및 인증 정보 설정
        if (StringUtils.hasText(token)) {
            log.info("📝 토큰 검증 시작");
            if (jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("✅ 인증 성공 - 사용자: {}, 권한: {}", authentication.getName(), authentication.getAuthorities());
            } else {
                log.error("❌ 토큰 검증 실패");
            }
        } else {
            log.warn("⚠️ 토큰이 없습니다 - URI: {}", requestURI);
        }

        filterChain.doFilter(request, response);
    }

    // Request Header에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        log.info("📨 Authorization 헤더 원본: {}", bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            String token = bearerToken.substring(BEARER_PREFIX.length());
            log.info("✂️ Bearer 제거 후 토큰: {}...", token.substring(0, Math.min(20, token.length())));
            return token;
        }

        log.warn("⚠️ Authorization 헤더가 없거나 Bearer로 시작하지 않음");
        return null;
    }



}
