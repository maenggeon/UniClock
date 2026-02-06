package com.uniclock.backend.domain.base.code.controller;

import com.uniclock.backend.domain.base.code.dto.CodeDetailResponseDTO;
import com.uniclock.backend.domain.base.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @GetMapping("/groups/{groupCode}/details")
    public ResponseEntity<List<CodeDetailResponseDTO>> getDetails(@PathVariable String groupCode) {
        return ResponseEntity.ok(codeService.getDetailsByGroup(groupCode));
    }
}
