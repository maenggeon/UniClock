package com.uniclock.backend.domain.user.controller;

import com.uniclock.backend.domain.user.data.request.LoginRequest;
import com.uniclock.backend.domain.user.data.request.SignUpRequest;
import com.uniclock.backend.domain.user.data.response.RefreshTokenRequest;
import com.uniclock.backend.domain.user.data.response.TokenResponse;
import com.uniclock.backend.domain.user.data.response.UserResponse;
import com.uniclock.backend.domain.user.service.AuthService;
import com.uniclock.backend.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        log.info("회원가입 요청: {}", request.getLoginId());
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("로그인 요청: {}", request.getLoginId());
        TokenResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 토큰 재발급
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("토큰 재발급 요청");
        TokenResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 로그인 ID 중복 확인
     */
    @GetMapping("/check/login-id")
    public ResponseEntity<Boolean> checkLoginId(@RequestParam String loginId) {
        log.info("로그인 ID 중복 확인: {}", loginId);
        boolean exists = userService.existsByLoginId(loginId);
        return ResponseEntity.ok(exists);
    }

}
