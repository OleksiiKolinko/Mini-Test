package com.example.demo.controller;

import com.example.demo.dto.ProcessRequest;
import com.example.demo.dto.ProcessResponse;
import com.example.demo.service.ProcessingLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProcessingLogController {
    private final ProcessingLogService processService;

    @PostMapping("/process")
    public ProcessResponse process(@RequestBody ProcessRequest request,
                                   Authentication authentication) {
        return processService.process(request.text(), authentication.getName());
    }
}
