package com.example.demo.service.impl;

import com.example.demo.dto.ProcessRequest;
import com.example.demo.dto.ProcessResponse;
import com.example.demo.model.ProcessingLog;
import com.example.demo.model.User;
import com.example.demo.repository.ProcessingLogRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProcessingLogService;
import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProcessingLogServiceImpl implements ProcessingLogService {
    private static final String HEADER_NAME = "X-Internal-Token";
    private static final String URL_TRANSFORM = "/api/transform";
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final ProcessingLogRepository logRepository;
    @Value("${service.b.url}")
    private String serviceUrl;
    @Value("${internal.token}")
    private String internalToken;

    public ProcessResponse process(String text, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_NAME, internalToken);
        HttpEntity<ProcessRequest> request =
                new HttpEntity<>(new ProcessRequest(text), headers);
        ResponseEntity<ProcessResponse> response =
                restTemplate.postForEntity(
                        serviceUrl + URL_TRANSFORM,
                        request, ProcessResponse.class);
        String result = Objects.requireNonNull(response.getBody()).result();
        User user = userRepository.findByEmail(email).orElseThrow();
        ProcessingLog log = ProcessingLog.builder().user(user).inputText(text).outputText(result)
                .createdAt(Instant.now()).build();
        logRepository.save(log);
        return new ProcessResponse(result);
    }
}
