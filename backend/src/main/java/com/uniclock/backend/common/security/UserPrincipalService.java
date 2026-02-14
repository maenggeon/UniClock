package com.uniclock.backend.common.security;

import com.uniclock.backend.domain.user.entity.User;
import com.uniclock.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginId));

        if(user.getDeletedAt() != null) {
            throw new UsernameNotFoundException("탈퇴한 사용자입니다: " + loginId);
        }

        return createUserDetails(user);
    }

    private UserDetails createUserDetails(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLoginId())
                .password(user.getPasswordHash())
                .authorities(authorities)
                .build();
    }

}
