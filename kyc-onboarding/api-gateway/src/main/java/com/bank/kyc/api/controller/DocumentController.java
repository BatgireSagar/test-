package com.bank.kyc.api.controller;

import com.bank.kyc.api.dto.DocumentUploadResponse;
import com.bank.kyc.api.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<DocumentUploadResponse> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("clientId") String clientId) {
        // Placeholder for service integration
        DocumentUploadResponse response = documentService.handleUpload(file, clientId);
        return ResponseEntity.ok(response);
    }
}