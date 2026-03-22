package com.example.demo.service;

import org.springframework.http.ResponseEntity;

public interface TransformService {
    ResponseEntity<?> transform(String token, String request);
}
