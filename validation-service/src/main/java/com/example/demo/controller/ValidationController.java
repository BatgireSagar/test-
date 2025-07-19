package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ValidationController {
    @PostMapping("/validate")
    public ResponseEntity<?> validate() {
        // TODO: Implement validation logic
        return ResponseEntity.ok("Validate endpoint stub");
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}