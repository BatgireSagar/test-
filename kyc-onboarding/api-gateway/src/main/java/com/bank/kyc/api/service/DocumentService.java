package com.bank.kyc.api.service;

import com.bank.kyc.api.dto.DocumentUploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {
    public DocumentUploadResponse handleUpload(MultipartFile file, String clientId) {
        // TODO: Store file, trigger OCR, return response
        return new DocumentUploadResponse("success", "File uploaded and processing started.");
    }
}