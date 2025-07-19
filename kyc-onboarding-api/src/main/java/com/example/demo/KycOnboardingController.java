package com.example.demo;

import com.example.demo.dto.OnboardingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/kyc")
@RequiredArgsConstructor
public class KycOnboardingController {
    private final KycOnboardingService onboardingService;

    @PostMapping(value = "/onboard", consumes = {"multipart/form-data"})
    public ResponseEntity<KycProfile> onboard(
            @RequestParam String clientType,
            @RequestParam String jurisdiction,
            @RequestParam String products,
            @RequestParam("files") List<MultipartFile> files
    ) throws IOException {
        OnboardingRequest req = new OnboardingRequest();
        req.setClientType(clientType);
        req.setJurisdiction(jurisdiction);
        req.setProducts(products);
        req.setFiles(files);
        KycProfile saved = onboardingService.onboard(req);
        return ResponseEntity.ok(saved);
    }
}