package com.uniclock.backend.domain.user.service;

import com.uniclock.backend.domain.user.data.request.SignUpRequest;
import com.uniclock.backend.domain.user.data.response.UserResponse;
import com.uniclock.backend.domain.user.entity.User;
import com.uniclock.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    @Transactional
    public UserResponse createUser(SignUpRequest request) {

        // 중복 체크
        validateDuplicateUser(request.getLoginId(), request.getEmail());

        // User 엔티티 생성
        User user = User.builder()
                .loginId(request.getLoginId())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .passwordUpdatedAt(LocalDateTime.now())
                .name(request.getName())
                .email(request.getEmail())
                .deletedAt(null)
                .build();

        User savedUser = userRepository.save(user);
        log.info("회원가입 완료: {}", savedUser.getLoginId());

        return UserResponse.from(savedUser);
    }

    /**
     * 회원 정보 수정
     */
    /*
    @Transactional
    public Long modifyUser(UserRequestDTO dto) throws AccessDeniedException {
    }
    */

    /**
     * 회원 정보 삭제
     */
    /*
    @Transactional
    public Long removeUser() {

    }
    */

    /**
     * 사용자 조회 by loginId
     */
    public UserResponse getUserByLoginId(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.getDeletedAt() != null) {
            throw new IllegalArgumentException("삭제된 사용자입니다.");
        }

        return UserResponse.from(user);
    }

    /**
     * 사용자 존재 확인
     */
    public boolean existsByLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    /**
     * 회원가입 시 중복 검사
     */
    private void validateDuplicateUser(String loginId, String email) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new IllegalArgumentException("이미 사용 중인 로그인 ID입니다.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
    }

}
