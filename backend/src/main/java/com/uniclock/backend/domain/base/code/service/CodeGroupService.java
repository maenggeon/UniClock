package com.uniclock.backend.domain.base.code.service;

import com.uniclock.backend.domain.base.code.data.request.CodeGroupCreateRequestDTO;
import com.uniclock.backend.domain.base.code.data.response.CodeDetailResponseDTO;
import com.uniclock.backend.domain.base.code.entity.CodeGroup;
import com.uniclock.backend.domain.base.code.repository.CodeDetailRepository;
import com.uniclock.backend.domain.base.code.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeGroupService {

    private final CodeGroupRepository groupRepository;
    private final CodeDetailRepository detailRepository;


    /**
     * 코드 그룹 저장
     */
    /*
    public void saveCodeGroup(CodeGroupCreateRequestDTO codeGroupDTO) {
        CodeGroup codeGroup = CodeGroup.builder()
                .groupCode(codeGroupDTO.getGroupCode())
                .groupName(codeGroupDTO.getGroupName())
                .build();

        groupRepository.save(codeGroup);
    }
    */

    /**
     * 모든 코드 그룹 출력
     */


}
