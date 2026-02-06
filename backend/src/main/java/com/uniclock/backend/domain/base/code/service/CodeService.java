package com.uniclock.backend.domain.base.code.service;

import com.uniclock.backend.domain.base.code.dto.CodeDetailResponseDTO;
import com.uniclock.backend.domain.base.code.entity.CodeGroup;
import com.uniclock.backend.domain.base.code.repository.CodeDetailRepository;
import com.uniclock.backend.domain.base.code.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeGroupRepository groupRepository;
    private final CodeDetailRepository detailRepository;

    public List<CodeGroup> getAllGroups() {
        return groupRepository.findAll();
    }

    public void saveGroup(CodeGroup group) {
        codeGroupRepository.save(group); // 상속받은 메서드 바로 사용
    }

    public List<CodeGroup> getAllGroups() {
        return codeGroupRepository.findAll();
    }


    public List<CodeDetailResponseDTO> getDetailsByGroup(String groupCode) {
        return deta
    }
}
