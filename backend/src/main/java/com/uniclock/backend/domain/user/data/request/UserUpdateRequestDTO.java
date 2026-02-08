package com.uniclock.backend.domain.user.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestDTO {

    @Size(min = 2, max = 20)
    private String name;

    @Email
    @Size(max = 100)
    private String email;

    @Size(min = 6, max = 20)
    private String newPassword;
}
