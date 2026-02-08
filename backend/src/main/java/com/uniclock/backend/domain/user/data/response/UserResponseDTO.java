package com.uniclock.backend.domain.user.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long userId;
    private String loginId;
    private String name;
    private String email;

    private LocalDateTime passwordUpdatedAt;

}
