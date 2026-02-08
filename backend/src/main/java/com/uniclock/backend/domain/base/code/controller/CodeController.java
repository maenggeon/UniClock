package com.uniclock.backend.domain.base.code.controller;

import com.uniclock.backend.domain.base.code.data.response.CodeDetailResponseDTO;
import com.uniclock.backend.domain.base.code.service.CodeGroupService;
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

    private final CodeGroupService codeService;


}
