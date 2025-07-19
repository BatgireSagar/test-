package com.example.demo;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationResult {
    private String documentType;
    private String status; // e.g., VALID, INVALID, MISSING
    private List<String> issues;
    private List<String> suggestedFixes;
}