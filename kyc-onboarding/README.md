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

- **LLM/Rule Engine (Configurable LLM Integration):**
  - You can use different LLMs for the Rule Engine microservice by setting environment variables—**no code changes required**:

    #### 1. OpenAI (default)
    - Set `LLM_PROVIDER=openai` (default) and `OPENAI_API_KEY=sk-...`.
    - See below for how to get your OpenAI API key.

    #### 2. Ollama (local LLM)
    - [Install Ollama](https://ollama.com/download) and pull a model (e.g., `ollama pull llama2`).
    - Start Ollama: `ollama run llama2`
    - Set `LLM_PROVIDER=ollama` and (optionally) `OLLAMA_API_URL=http://localhost:11434`.

    #### 3. Hugging Face Inference API
    - Set `LLM_PROVIDER=huggingface`, `HUGGINGFACE_API_TOKEN=hf_...`, and (optionally) `HUGGINGFACE_MODEL=meta-llama/Llama-2-7b-chat-hf`.
    - Get your Hugging Face token from [https://huggingface.co/settings/tokens](https://huggingface.co/settings/tokens).

    #### 4. Dummy Provider (for testing)
    - Set `LLM_PROVIDER=dummy` to use a static/dummy response.

    #### Example (in your shell or docker-compose.yml):
    ```sh
    export LLM_PROVIDER=ollama
    export OLLAMA_API_URL=http://localhost:11434
    # or for OpenAI:
    # export LLM_PROVIDER=openai
    # export OPENAI_API_KEY=sk-...
    ```

    **No code changes are needed to switch LLMs—just set the environment variables!**

  - **How to get your OpenAI API key:**
    1. Go to [https://platform.openai.com/signup](https://platform.openai.com/signup) and sign up (or [log in](https://platform.openai.com/login) if you already have an account).
    2. Navigate to the [API Keys page](https://platform.openai.com/api-keys) or click your profile icon (top right) → “View API keys”.
    3. Click **“Create new secret key”** and give it a name (e.g., "kyc-onboarding-local").
    4. Copy the generated key (it will look like `sk-...`).
    5. Set the key as an environment variable in your terminal before running Docker Compose:
       ```sh
       export OPENAI_API_KEY=sk-...yourkey...
       docker-compose up --build
       ```
       Or add it to the `llm-rule-engine` service in `docker-compose.yml`:
       ```yaml
       llm-rule-engine:
         build: ../llm-rule-engine
         ports:
           - "8091:8091"
         environment:
           - OPENAI_API_KEY=sk-...yourkey...
       ```
    - **Important:**
      - Keep your API key secret! Never commit it to source control.
      - OpenAI offers a free trial with limited usage. After that, you may need to add billing information.
      - If you hit usage limits, you may need to upgrade your OpenAI account.

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

## Local Setup Example: Step-by-Step

Follow these steps to clone and run the system locally. Adjust the LLM provider section as needed for your use case.

### 1. Clone the Repository
```sh
git clone <your-repo-url>
cd kyc-onboarding/docker
```

### 2. Choose and Configure Your LLM Provider

#### **A. OpenAI (default, cloud-based)**
1. [Get your OpenAI API key](https://platform.openai.com/api-keys) (see instructions above).
2. In your terminal:
   ```sh
   export LLM_PROVIDER=openai
   export OPENAI_API_KEY=sk-...yourkey...
   docker-compose up --build
   ```

#### **B. Ollama (local LLM, recommended for privacy/offline)**
1. [Install Ollama](https://ollama.com/download) for your OS.
2. Pull a model (e.g., Llama 2):
   ```sh
   ollama pull llama2
   ollama run llama2
   # (keep this running in a separate terminal)
   ```
3. In a new terminal, from the `kyc-onboarding/docker` directory:
   ```sh
   export LLM_PROVIDER=ollama
   export OLLAMA_API_URL=http://localhost:11434
   docker-compose up --build
   ```

#### **C. Hugging Face Inference API (cloud-based)**
1. [Get your Hugging Face API token](https://huggingface.co/settings/tokens).
2. In your terminal:
   ```sh
   export LLM_PROVIDER=huggingface
   export HUGGINGFACE_API_TOKEN=hf_...yourtoken...
   export HUGGINGFACE_MODEL=meta-llama/Llama-2-7b-chat-hf  # or your preferred model
   docker-compose up --build
   ```

#### **D. Dummy Provider (for testing only)**
```sh
export LLM_PROVIDER=dummy
docker-compose up --build
```

### 3. Access the Services
- API Gateway: http://localhost:8080
- Camunda: http://localhost:8081
- OCR Service: http://localhost:8090
- LLM/Rule Engine: http://localhost:8091
- Validation Engine: http://localhost:8092
- Grafana: http://localhost:3000
- Ops UI: http://localhost:3000 (if running)

### 4. Stopping and Cleaning Up
- Stop all services: `Ctrl+C` then `docker-compose down`
- Remove unused containers/images: `docker system prune -af`

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