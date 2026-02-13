package com.uniclock.backend.domain.user.controller;

import com.uniclock.backend.domain.user.data.request.LoginRequestDTO;
import com.uniclock.backend.domain.user.data.request.UserRequestDTO;
import com.uniclock.backend.domain.user.service.AuthService;
import com.uniclock.backend.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * 회원가입
     */

    /*
    @PostMapping("/join")
    public ResponseEntity<Long> signUp(@RequestBody @Valid UserRequestDTO dto) {
        Long userId = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }
    */

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto) {
        String token = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    /**
     * 회원 상세 조회
     */
    /*
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> userDetails(@PathVariable Long userId) {
        UserResponseDTO response = userService.findById(userId);
        return ResponseEntity.ok(response);
    }
    */


    /**
     * 회원 정보 수정
     */
    /*
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> update(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateRequestDTO dto) {
        userService.update(userId, dto);
        return ResponseEntity.ok().build();
    }
    */

     /**
     * 회원 탈퇴 (Soft Delete)
     */
     /*
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

      */
}
