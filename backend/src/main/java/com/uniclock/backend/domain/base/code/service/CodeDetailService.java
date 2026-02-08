package com.uniclock.backend.domain.base.code.service;

import com.uniclock.backend.domain.base.code.data.response.CodeDetailResponseDTO;
import com.uniclock.backend.domain.base.code.entity.CodeDetail;
import com.uniclock.backend.domain.base.code.repository.CodeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeDetailService {

    private final CodeDetailRepository detailRepository;

    /*
    public List<CodeDetailResponseDTO> findActiveCodes(String groupCode) {
        return detailRepository.findActiveByGroupCode(groupCode)
                .stream()
                .map(codeDetail -> CodeDetailResponseDTO.builder()
                        .codeId(codeDetail.getCodeId())
                        .codeName(codeDetail.getCodeName())
                        .build())
                .toList();
    }
     */
}
