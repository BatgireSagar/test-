package com.bank.kyc.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentUploadResponse {
    private String status;
    private String message;
}