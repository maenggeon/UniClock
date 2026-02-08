package com.uniclock.backend.domain.user.service;

import com.uniclock.backend.domain.user.data.request.UserCreateRequestDTO;
import com.uniclock.backend.domain.user.data.request.UserUpdateRequestDTO;
import com.uniclock.backend.domain.user.data.response.UserResponseDTO;
import com.uniclock.backend.domain.user.entity.User;
import com.uniclock.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 (Create)
     */
    @Transactional
    public Long join(UserCreateRequestDTO dto) {
        // 1. 중복 검증
        validateDuplicateUser(dto.getLoginId(), dto.getEmail());

        // 2. 비밀번호 암호화 및 엔티티 변환
        User user = User.builder()
                .loginId(dto.getLoginId())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .build();

        return userRepository.save(user).getUserId();
    }

    /**
     * 회원 정보 수정 (Update)
     */
    @Transactional
    public void update(Long userId, UserUpdateRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. ID: " + userId));

        // 이름 수정
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        // 이메일 수정 (중복 체크 포함)
        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new IllegalStateException("이미 사용 중인 이메일입니다.");
            }
            user.setEmail(dto.getEmail());
        }

        // 비밀번호 수정
        if (dto.getNewPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));
            user.setPasswordUpdatedAt(LocalDateTime.now());
        }

        // 별도의 save() 호출 없이도 Transaction 종료 시 Dirty Checking으로 자동 반영됩니다.
    }

    /**
     * 회원 단건 조회 (Read)
     */
    public UserResponseDTO findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .name(user.getName())
                .email(user.getEmail())
                .passwordUpdatedAt(user.getPasswordUpdatedAt())
                .build();
    }

    /**
     * 회원 삭제 (Delete - Soft Delete)
     */
    @Transactional
    public void delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 이미 존재하지 않습니다."));
        userRepository.delete(user);
        // @SQLDelete 설정으로 인해 실제로는 UPDATE 쿼리가 실행됩니다.
    }

    private void validateDuplicateUser(String loginId, String email) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }
}
