package com.uniclock.backend.domain.user.data.response;

import com.uniclock.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUserDTO {

    private Long userId;
    private String loginId;
    private String email;
    private String name;

    /**
     * User entity -> DTO
     */
    public static JwtUserDTO from(User user) {
        return JwtUserDTO.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
