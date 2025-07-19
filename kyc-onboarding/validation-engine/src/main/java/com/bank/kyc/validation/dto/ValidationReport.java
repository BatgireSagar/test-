package com.bank.kyc.validation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationReport {
    private String status;
    private List<String> validated;
    private List<String> missing;
    private List<String> invalid;
    private List<String> mismatched;
    private List<Map<String, Object>> auditTrail;
}