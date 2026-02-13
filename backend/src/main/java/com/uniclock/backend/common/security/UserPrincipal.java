package com.uniclock.backend.common.security;

import com.uniclock.backend.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    private final User user;

    /**
     * 사용자 권한 목록 반환
     * User 엔터티의 authorities를 GrantedAuthority로 변환
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * 사용자 비밀번호 반환
     */
    @Override
    public @Nullable String getPassword() {
        return user.getPasswordHash();
    }

    /**
     * 사용자 식별자 반환 (userId 사용)
     */
    @Override
    public String getUsername() {
        return user.getUserId().toString();
    }

    /**
     * 계정이 만료되지 않았는지 확인
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정이 잠겨있지 않은지 확인
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // 현재는 계정 잠금 기능 미사용
    }

    /**
     * 자격 증명(비밀번호)이 만료되지 않았는지 확인
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 현재는 자격 증명 만료 기능 미사용
    }

    /**
     * 계정이 활성화되어 있는지 확인
     */
    @Override
    public boolean isEnabled() {
        return user.getDeletedAt() == null;
    }

    /**
     * User 엔터티 반환 (추가 정보 접근용)
     */
    public User getUser() {
        return user;
    }
}
