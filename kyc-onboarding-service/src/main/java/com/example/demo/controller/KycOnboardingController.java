package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kyc")
public class KycOnboardingController {
    @PostMapping("/onboard")
    public ResponseEntity<?> onboard() {
        // TODO: Implement onboarding logic
        return ResponseEntity.ok("Onboard endpoint stub");
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}