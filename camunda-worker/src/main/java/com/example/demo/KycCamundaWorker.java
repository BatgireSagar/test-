package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KycCamundaWorker implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // TODO: Connect to Camunda 8 External Task API (Zeebe or SaaS)
        // Subscribe to topic 'kyc-validate-documents'
        // For each task:
        //   1. Download document (simulate or fetch from kyc-onboarding-api)
        //   2. Call document-parser-service /parse endpoint
        //   3. Call validation-service /validate endpoint
        //   4. Call LLM agent (OpenAI or LangChain) for missing doc suggestions
        //   5. Complete the task in Camunda
        System.out.println("[Stub] Camunda worker started. Would listen to 'kyc-validate-documents'.");
    }
}