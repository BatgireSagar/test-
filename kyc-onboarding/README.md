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

---

## Project Setup Details

### Prerequisites
- **Docker Desktop** (Windows, Mac, or Linux): [Download here](https://www.docker.com/products/docker-desktop/)
- **Git**: For cloning the repository
- **Node.js 18+** (for Ops UI development)
- **Java 17+** (for local Java service development)
- **Python 3.9+** (for local Python service development)

### Environment Variables
Some services require environment variables for full functionality:

- **LLM/Rule Engine (OpenAI integration):**
  - Set `OPENAI_API_KEY` to your OpenAI API key to enable real LLM extraction.
  - You can set this in your shell before running Docker Compose:
    ```sh
    export OPENAI_API_KEY=sk-...yourkey...
    docker-compose up --build
    ```
  - Or add it to the `llm-rule-engine` service in `docker-compose.yml`:
    ```yaml
    llm-rule-engine:
      build: ../llm-rule-engine
      ports:
        - "8091:8091"
      environment:
        - OPENAI_API_KEY=sk-...yourkey...
    ```

- **Database credentials** are set in `docker-compose.yml` and `application.properties` (default: user `kyc`, password `kycpass`).

### First-Time Setup (Development)
- **Clone the repository:**
  ```sh
  git clone <your-repo-url>
  cd kyc-onboarding/docker
  ```
- **Build and run all services:**
  ```sh
  docker-compose up --build
  ```
- **(Optional) Run Ops UI in development mode:**
  ```sh
  cd ../ops-ui
  npm install --legacy-peer-deps
  npm start
  ```

### Service Ports
| Service            | URL/Port                   |
|--------------------|---------------------------|
| API Gateway        | http://localhost:8080      |
| Camunda Workflow   | http://localhost:8081      |
| OCR Service        | http://localhost:8090      |
| LLM/Rule Engine    | http://localhost:8091      |
| Validation Engine  | http://localhost:8092      |
| PostgreSQL         | localhost:5432 (DB)        |
| Prometheus         | http://localhost:9090      |
| Grafana            | http://localhost:3000      |
| Ops UI (React)     | http://localhost:3000      |

### Stopping and Cleaning Up
- Stop all services: `Ctrl+C` then `docker-compose down`
- Remove unused containers/images: `docker system prune -af`

### Troubleshooting
- If you get port conflicts, ensure nothing else is running on the listed ports.
- If you change code, re-run `docker-compose up --build` to rebuild images.
- View logs for a service: `docker-compose logs <service-name>`

---

## How to Run the App Locally with Docker Desktop

(See above for detailed steps.)

---

## Quickstart (Summary Table)

| Step | Command/Action |
|------|----------------|
| 1    | Install Docker Desktop, Git, Node.js, Java, Python |
| 2    | `git clone <repo>` and `cd kyc-onboarding/docker` |
| 3    | Set environment variables as needed |
| 4    | `docker-compose up --build` |
| 5    | Access services at listed URLs |
| 6    | `Ctrl+C` then `docker-compose down` to stop |
| 7    | Troubleshoot as needed |
| 8    | (Optional) `docker system prune -af` |

---

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