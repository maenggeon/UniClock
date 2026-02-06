package com.uniclock.backend.domain.base.code.dto;

import lombok.Data;

import java.util.List;

@Data
public class CodeGroupCreateRequestDTO {
    private String groupCode;
    private String groupName;
    private List<CodeDetailResponseDTO> details;

}
