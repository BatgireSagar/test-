package com.bank.kyc.validation.controller;

import com.bank.kyc.validation.dto.ValidationRequest;
import com.bank.kyc.validation.dto.ValidationReport;
import com.bank.kyc.validation.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validate")
@RequiredArgsConstructor
public class ValidationController {
    private final ValidationService validationService;

    @PostMapping
    public ResponseEntity<ValidationReport> validate(@RequestBody ValidationRequest request) {
        ValidationReport report = validationService.validate(request);
        return ResponseEntity.ok(report);
    }
}