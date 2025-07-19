package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DocumentParserController {
    @PostMapping("/parse")
    public ResponseEntity<?> parse() {
        // TODO: Implement document parsing logic
        return ResponseEntity.ok("Parse endpoint stub");
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}