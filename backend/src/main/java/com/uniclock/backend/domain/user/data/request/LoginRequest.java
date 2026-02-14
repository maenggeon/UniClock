package com.uniclock.backend.domain.user.data.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotNull
    private String loginId;

    @NotNull
    private String password;
}
