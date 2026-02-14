package com.uniclock.backend.domain.base.code.service;

import com.uniclock.backend.domain.base.code.repository.CodeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
