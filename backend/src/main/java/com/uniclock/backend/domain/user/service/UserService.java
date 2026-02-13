package com.uniclock.backend.domain.user.service;

import com.uniclock.backend.domain.user.data.request.UserRequestDTO;
import com.uniclock.backend.domain.user.entity.User;
import com.uniclock.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createUser(UserRequestDTO dto) {

        validateDuplicateUser(dto);

        User user = User.builder()
                .loginId(dto.getLoginId())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .deletedAt(null)
                .build();

        userRepository.save(user);
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
     * login id 중복 여부 확인
     */
    @Transactional(readOnly = true)
    private boolean isDuplicateLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    /**
     * 이메일 중복 여부 확인
     */
    @Transactional(readOnly = true)
    private boolean isDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * 회원가입 시 중복 검사
     */
    private void validateDuplicateUser(UserRequestDTO dto) {
        if (isDuplicateLoginId(dto.getLoginId())) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        if (isDuplicateEmail(dto.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

}
