package com.uniclock.backend.domain.base.code.data.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeGroupResponse {

    private String groupCode;
    private String groupName;
    private List<CodeDetailResponse> details;
}
