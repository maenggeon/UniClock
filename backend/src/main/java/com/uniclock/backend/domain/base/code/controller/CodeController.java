package com.uniclock.backend.domain.base.code.controller;

import com.uniclock.backend.domain.base.code.service.CodeGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeController {

    private final CodeGroupService codeService;


}
