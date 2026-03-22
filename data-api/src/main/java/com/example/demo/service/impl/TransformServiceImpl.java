package com.example.demo.service.impl;

import com.example.demo.dto.TransformResponse;
import com.example.demo.service.TransformService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransformServiceImpl implements TransformService {
    @Value("${internal.token}")
    private String internalToken;

    @Override
    public ResponseEntity<?> transform(String token, String text) {
        if (token == null || !token.equals(internalToken)) {
            return ResponseEntity.status(403).build();
        }
        String result = new StringBuilder(text).reverse().toString().toUpperCase();
        return ResponseEntity.ok(new TransformResponse(result));
    }
}
