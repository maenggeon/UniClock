package com.uniclock.backend.domain.base.code.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeDetailResponse {

    private String codeId;
    private String codeName;
}
