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
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final ProcessingLogRepository logRepository;
    @Value("${internal.token}")
    private String internalToken;
    @Value("${service.b.url}")
    private String serviceUrl;

    public ProcessResponse process(String text, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Token", internalToken);
        HttpEntity<ProcessRequest> request =
                new HttpEntity<>(new ProcessRequest(text), headers);
        ResponseEntity<ProcessResponse> response =
                restTemplate.postForEntity(
                        serviceUrl + "/api/transform",
                        request, ProcessResponse.class);
        String result = Objects.requireNonNull(response.getBody()).result();
        User user = userRepository.findByEmail(email).orElseThrow();
        ProcessingLog log = ProcessingLog.builder().user(user).inputText(text).outputText(result)
                .createdAt(Instant.now()).build();
        logRepository.save(log);
        return new ProcessResponse(result);
    }
}
