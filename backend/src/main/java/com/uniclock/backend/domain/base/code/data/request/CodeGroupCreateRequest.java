package com.uniclock.backend.domain.base.code.data.request;

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
public class CodeGroupCreateRequest {

    @NotBlank
    @Size(max = 20)
    private String groupCode;

    @NotBlank
    @Size(max = 50)
    private String groupName;
}
