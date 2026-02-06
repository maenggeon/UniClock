package com.uniclock.backend.domain.base.code.dto;

import lombok.Data;

@Data
public class CodeDetailResponseDTO {
    private String codeId;
    private String codeName;
    private Integer sortOrder;
    private Boolean isUsed;
}
