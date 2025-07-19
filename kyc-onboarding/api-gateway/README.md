# KYC Onboarding API Gateway

This is the main REST API service for the AI-powered Client Onboarding Automation System for Investment and Corporate Banking KYC processes.

## Features
- Document upload and ingestion
- Integration with OCR, LLM/Rule Engine, Validation Engine, and Camunda Workflow
- Modular, extensible, and resilient architecture
- OpenAPI documentation

## Requirements
- Java 17+
- Maven 3.8+
- PostgreSQL (for local development)

## Setup
1. Clone the repository and navigate to this directory:
   ```sh
   cd kyc-onboarding/api-gateway
   ```
2. Build the project:
   ```sh
   mvn clean install
   ```
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Access Swagger UI at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Endpoints
- `POST /api/v1/documents/upload` — Upload a document for onboarding

## Configuration
- Edit `src/main/resources/application.properties` for DB and service endpoints.

## Extensibility
- Add new controllers/services in the appropriate packages.
- Use the `/common` module for shared DTOs and utilities.

## License
MIT