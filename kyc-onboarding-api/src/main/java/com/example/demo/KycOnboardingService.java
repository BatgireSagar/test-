package com.example.demo;

import com.example.demo.dto.OnboardingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KycOnboardingService {
    private final KycProfileRepository kycProfileRepository;
    private final KycDocumentRepository kycDocumentRepository;

    private final String uploadDir = "/tmp/kyc-uploads";

    @Transactional
    public KycProfile onboard(OnboardingRequest request) throws IOException {
        // Ensure upload directory exists
        Files.createDirectories(Path.of(uploadDir));

        List<KycDocument> docs = new ArrayList<>();
        if (request.getFiles() != null) {
            for (MultipartFile file : request.getFiles()) {
                String filePath = uploadDir + File.separator + file.getOriginalFilename();
                file.transferTo(new File(filePath));
                KycDocument doc = KycDocument.builder()
                        .type(file.getOriginalFilename()) // In real use, infer type
                        .fileName(file.getOriginalFilename())
                        .filePath(filePath)
                        .build();
                docs.add(doc);
            }
        }
        KycProfile profile = KycProfile.builder()
                .clientType(request.getClientType())
                .jurisdiction(request.getJurisdiction())
                .products(request.getProducts())
                .submittedDocs(docs)
                .build();
        docs.forEach(d -> d.setKycProfile(profile));
        KycProfile saved = kycProfileRepository.save(profile);

        // TODO: Call Camunda REST API to start BPMN instance (stub)
        // startCamundaWorkflow(saved);

        return saved;
    }

    // Stub for Camunda integration
    public void startCamundaWorkflow(KycProfile profile) {
        // Use RestTemplate or WebClient to call Camunda REST API
    }
}