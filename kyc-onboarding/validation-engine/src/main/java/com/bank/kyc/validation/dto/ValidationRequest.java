package com.bank.kyc.validation.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ValidationRequest {
    private Map<String, Object> clientData;
    private Map<String, Object> requirements;
}