package com.uniclock.backend.domain.user.service;


import com.uniclock.backend.common.security.JwtTokenProvider;
import com.uniclock.backend.domain.user.data.request.LoginRequest;
import com.uniclock.backend.domain.user.data.response.RefreshTokenRequest;
import com.uniclock.backend.domain.user.data.response.TokenResponse;
import com.uniclock.backend.domain.user.entity.User;
import com.uniclock.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인
     */
    public TokenResponse login(LoginRequest request) {
        // 사용자 조회
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));

        // 삭제된 사용자 체크
        if (user.getDeletedAt() != null) {
            throw new IllegalArgumentException("삭제된 사용자입니다.");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(user.getLoginId(), "ROLE_USER");
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getLoginId());

        log.info("로그인 성공: {}", user.getLoginId());

        return TokenResponse.of(accessToken, refreshToken);
    }

    /**
     * 토큰 재발급
     */
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        // Refresh Token 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token입니다.");
        }

        // Refresh Token에서 loginId 추출
        String loginId = jwtTokenProvider.getLoginId(refreshToken);

        // 사용자 존재 여부 확인
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 삭제된 사용자 체크
        if (user.getDeletedAt() != null) {
            throw new IllegalArgumentException("삭제된 사용자입니다.");
        }

        // 새로운 Access Token 발급
        String newAccessToken = jwtTokenProvider.createAccessToken(loginId, "ROLE_USER");
        String newRefreshToken = jwtTokenProvider.createRefreshToken(loginId);

        log.info("토큰 재발급 성공: {}", loginId);

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }
}