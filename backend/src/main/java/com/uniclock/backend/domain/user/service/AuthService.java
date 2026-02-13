package com.uniclock.backend.domain.user.service;


import com.uniclock.backend.common.security.JwtTokenProvider;
import com.uniclock.backend.domain.user.data.response.JwtUserDTO;
import com.uniclock.backend.domain.user.data.request.LoginRequestDTO;
import com.uniclock.backend.domain.user.entity.User;
import com.uniclock.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String login(LoginRequestDTO dto) {
        String loginId = dto.getLoginId();
        String password = dto.getPassword();
        Optional<User> user = userRepository.findByLoginId(loginId);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
        }

        if (!passwordEncoder.matches(password, user.get().getPasswordHash())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        JwtUserDTO jwtUserDTO = JwtUserDTO.from(user.get());

        return jwtTokenProvider.createAccessToken(jwtUserDTO);
    }

}