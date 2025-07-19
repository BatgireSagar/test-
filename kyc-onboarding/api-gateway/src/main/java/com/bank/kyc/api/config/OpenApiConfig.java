package com.bank.kyc.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("KYC Onboarding API Gateway")
                        .version("1.0.0")
                        .description("REST API for KYC onboarding automation in investment and corporate banking."));
    }
}