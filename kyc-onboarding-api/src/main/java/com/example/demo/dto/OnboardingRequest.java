package com.example.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class OnboardingRequest {
    private String clientType;
    private String jurisdiction;
    private String products;
    private List<MultipartFile> files;
}