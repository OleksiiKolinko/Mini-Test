package com.example.demo.service;

import com.example.demo.dto.ProcessResponse;

public interface ProcessingLogService {

    ProcessResponse process(String text, String email);
}
