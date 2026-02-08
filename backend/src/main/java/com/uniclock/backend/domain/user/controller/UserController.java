package com.uniclock.backend.domain.user.controller;

import com.uniclock.backend.domain.user.data.request.UserCreateRequestDTO;
import com.uniclock.backend.domain.user.data.request.UserUpdateRequestDTO;
import com.uniclock.backend.domain.user.data.response.UserResponseDTO;
import com.uniclock.backend.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping
    public ResponseEntity<Long> join(@RequestBody @Valid UserCreateRequestDTO dto) {
        Long userId = userService.join(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    /**
     * 회원 상세 조회
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserInfo(@PathVariable Long userId) {
        UserResponseDTO response = userService.findById(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 회원 정보 수정
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUserInfo(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateRequestDTO dto) {
        userService.update(userId, dto);
        return ResponseEntity.ok().build();
    }

    /**
     * 회원 탈퇴 (Soft Delete)
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
