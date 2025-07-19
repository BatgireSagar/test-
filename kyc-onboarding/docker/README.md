# Docker Compose Setup

This directory contains the Docker Compose configuration to orchestrate all microservices for the KYC onboarding automation system.

## Services
- **api-gateway**: Main REST API (http://localhost:8080)
- **camunda**: Camunda BPMN workflow engine (http://localhost:8081)
- **ocr-service**: OCR microservice (http://localhost:8090)
- **llm-rule-engine**: LLM/Rule Engine microservice (http://localhost:8091)
- **validation-engine**: Validation Engine microservice (http://localhost:8092)
- **db**: PostgreSQL database (localhost:5432)

## How to Run
1. From the `docker` directory, build and start all services:
   ```sh
   docker-compose up --build
   ```
2. Access the services at the ports listed above.

## Notes
- Ensure you have Docker and Docker Compose installed.
- The first build may take several minutes.
- Update service endpoints and environment variables as needed for your environment.