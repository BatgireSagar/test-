# AI-Powered KYC Onboarding Automation System

This project automates and streamlines the client onboarding and KYC process for Investment and Corporate Banking using AI, OCR, and workflow automation.

## Architecture
- **API Gateway**: Main REST API for document ingestion and orchestration
- **OCR Service**: Extracts fields from scanned/PDF documents
- **LLM/Rule Engine**: Extracts onboarding requirements from PP docs using LLMs or rules
- **Validation Engine**: Validates client data against requirements
- **Camunda Workflow**: Orchestrates onboarding, validation, and manual review
- **PostgreSQL**: Stores KYC data and audit logs

See [docs/architecture.md](docs/architecture.md) for a full diagram and component details.

## Quickstart
1. Clone the repo and navigate to the `docker` directory:
   ```sh
   cd kyc-onboarding/docker
   docker-compose up --build
   ```
2. Access services:
   - API Gateway: http://localhost:8080
   - Camunda: http://localhost:8081
   - OCR: http://localhost:8090
   - LLM/Rule Engine: http://localhost:8091
   - Validation Engine: http://localhost:8092

## Sample Data
- [sample-data/pp-doc-sample.txt](sample-data/pp-doc-sample.txt): Example PP doc
- [sample-data/client-profile-sample.json](sample-data/client-profile-sample.json): Example client profile

## Documentation
- [docs/architecture.md](docs/architecture.md): System architecture
- [camunda-workflow/docs/workflow-overview.md](../camunda-workflow/docs/workflow-overview.md): BPMN workflow

## Extensibility
- Modular microservices for easy extension
- Add new OCR/LLM providers, rules, or products as needed

## License
MIT