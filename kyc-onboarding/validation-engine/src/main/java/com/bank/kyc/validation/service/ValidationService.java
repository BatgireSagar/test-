package com.bank.kyc.validation.service;

import com.bank.kyc.validation.dto.ValidationRequest;
import com.bank.kyc.validation.dto.ValidationReport;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ValidationService {
    public ValidationReport validate(ValidationRequest request) {
        // TODO: Implement real validation logic
        return new ValidationReport(
                "manual_review_required",
                List.of("Entity Name", "Incorporation Country"),
                List.of("UBO Declaration"),
                List.of("Expired Passport"),
                List.of("Form Type: W8BEN instead of W8BEN-E"),
                List.of(Map.of("step", "validation", "result", "manual_review_required"))
        );
    }
}