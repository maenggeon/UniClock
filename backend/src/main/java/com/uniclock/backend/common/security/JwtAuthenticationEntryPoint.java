package com.uniclock.backend.common.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        // Filter에서 설정한 예외 코드를 가져옴
        String exception = (String) request.getAttribute("exception");

        log.error("인증 실패 - Exception: {}, Message: {}", exception, authException.getMessage());

        setResponse(response, exception);
    }


    private void setResponse(HttpServletResponse response, String exceptionCode) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");

        // 상세 에러 메시지 처리 (커스텀 에러 코드가 있다면 여기서 매핑)
        if ("EXPIRED_TOKEN".equals(exceptionCode)) {
            body.put("code", "TOKEN_EXPIRED");
            body.put("message", "토큰이 만료되었습니다. 다시 로그인해주세요.");
        } else if ("INVALID_TOKEN".equals(exceptionCode)) {
            body.put("code", "TOKEN_INVALID");
            body.put("message", "유효하지 않은 토큰입니다.");
        } else {
            body.put("code", "AUTHENTICATION_FAILED");
            body.put("message", "인증에 실패하였습니다.");
        }

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
