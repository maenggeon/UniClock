package com.uniclock.backend.domain.user.controller;

import com.uniclock.backend.domain.user.data.response.UserResponse;
import com.uniclock.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 내 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo() {
        String loginId = getCurrentLoginId();
        log.info("내 정보 조회: {}", loginId);
        UserResponse response = userService.getUserByLoginId(loginId);
        return ResponseEntity.ok(response);
    }

    /**
     * 현재 로그인한 사용자의 loginId 가져오기
     */
    private String getCurrentLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증되지 않은 사용자입니다.");
        }
        return authentication.getName();
    }
}
