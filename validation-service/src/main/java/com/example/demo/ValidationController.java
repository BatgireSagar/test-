package com.example.demo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ValidationController {
    private final ValidationService validationService;

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody ValidationRequest req) {
        var result = validationService.validate(req.getParsedData(), req.getClientType(), req.getJurisdiction(), req.getProducts());
        return ResponseEntity.ok(result);
    }

    @Data
    public static class ValidationRequest {
        private Map<String, String> parsedData;
        private String clientType;
        private String jurisdiction;
        private String products;
    }
}