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

## How to Run the App Locally with Docker Desktop

### 1. Prerequisites
- **Install [Docker Desktop](https://www.docker.com/products/docker-desktop/)** for your OS (Windows, Mac, or Linux).
- Make sure Docker Desktop is running.

### 2. Clone the Repository
```sh
git clone <your-repo-url>
cd kyc-onboarding/docker
```
*(Replace `<your-repo-url>` with your actual repository URL.)*

### 3. Build and Start All Services
From the `kyc-onboarding/docker` directory, run:
```sh
docker-compose up --build
```
- This command will build all microservices and start the containers.
- The first build may take several minutes as it downloads dependencies and builds images.

### 4. Access the Services
Once all containers are running, you can access the system at the following URLs:

| Service            | URL                          |
|--------------------|-----------------------------|
| API Gateway        | http://localhost:8080        |
| Camunda Workflow   | http://localhost:8081        |
| OCR Service        | http://localhost:8090        |
| LLM/Rule Engine    | http://localhost:8091        |
| Validation Engine  | http://localhost:8092        |
| PostgreSQL         | localhost:5432 (DB)          |
| Prometheus         | http://localhost:9090        |
| Grafana            | http://localhost:3000        |
| Ops UI (React)     | http://localhost:3000        |

> **Note:** If you want to run the Ops UI separately (for hot-reloading during development), open a new terminal:
> ```sh
> cd kyc-onboarding/ops-ui
> npm install --legacy-peer-deps
> npm start
> ```
> Then access it at [http://localhost:3000](http://localhost:3000).

### 5. Stopping the System
To stop all services, press `Ctrl+C` in the terminal where Docker Compose is running, then:
```sh
docker-compose down
```

### 6. Troubleshooting
- If you get port conflicts, make sure nothing else is running on the ports listed above.
- If you make code changes, re-run `docker-compose up --build` to rebuild the images.
- You can view logs for a specific service:
  ```sh
  docker-compose logs api-gateway
  ```

### 7. (Optional) Clean Up Docker Resources
To remove all stopped containers, unused networks, and dangling images:
```sh
docker system prune -af
```

---

## Quickstart (Summary Table)

| Step | Command/Action |
|------|----------------|
| 1    | Install Docker Desktop |
| 2    | `git clone <repo>` and `cd kyc-onboarding/docker` |
| 3    | `docker-compose up --build` |
| 4    | Access services at listed URLs |
| 5    | `Ctrl+C` then `docker-compose down` to stop |
| 6    | Troubleshoot as needed |
| 7    | (Optional) `docker system prune -af` |

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