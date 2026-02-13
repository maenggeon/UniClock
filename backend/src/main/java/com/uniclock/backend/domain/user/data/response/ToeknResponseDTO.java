package com.uniclock.backend.domain.user.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ToeknResponseDTO {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}
