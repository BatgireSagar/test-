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

## Local Setup Example: Step-by-Step (Detailed)

Follow these steps to clone and run the system locally. Adjust the LLM provider section as needed for your use case.

### 1. Prerequisites
- **Docker Desktop**: [Download and install](https://www.docker.com/products/docker-desktop/) for your OS (Windows, Mac, Linux). Start Docker Desktop before continuing.
- **Git**: [Download here](https://git-scm.com/downloads) if not already installed.
- **(Optional for UI/Service Dev)**: Node.js 18+, Java 17+, Python 3.9+ (see Project Setup Details above).

### 2. Clone the Repository
```sh
git clone <your-repo-url>
cd kyc-onboarding/docker
```
*(Replace `<your-repo-url>` with your actual repository URL.)*

### 3. Choose and Configure Your LLM Provider

#### **A. OpenAI (default, cloud-based)**
1. **Get your OpenAI API key:**
   - Go to [https://platform.openai.com/signup](https://platform.openai.com/signup) and sign up (or [log in](https://platform.openai.com/login)).
   - Click your profile icon (top right) → “View API keys” or go to [https://platform.openai.com/api-keys](https://platform.openai.com/api-keys).
   - Click **“Create new secret key”**, name it (e.g., "kyc-onboarding-local"), and copy the key (starts with `sk-`).
2. **Run the system:**
   ```sh
   export LLM_PROVIDER=openai
   export OPENAI_API_KEY=sk-...yourkey...
   docker-compose up --build
   ```
   - *If using Windows CMD, use `set` instead of `export`.*
   - *If using PowerShell, use `$env:LLM_PROVIDER="openai"` and `$env:OPENAI_API_KEY="sk-..."`.*

#### **B. Ollama (local LLM, recommended for privacy/offline)**
1. **Install Ollama:**
   - Go to [https://ollama.com/download](https://ollama.com/download) and download for your OS. Follow the install instructions.
2. **Pull and run a model:**
   ```sh
   ollama pull llama2
   ollama run llama2
   ```
   - *Keep the `ollama run` command running in a separate terminal window.*
3. **Run the system:**
   In a new terminal, from the `kyc-onboarding/docker` directory:
   ```sh
   export LLM_PROVIDER=ollama
   export OLLAMA_API_URL=http://localhost:11434
   docker-compose up --build
   ```
   - *If you want to use a different model, change the `model` in the Ollama provider or set an env variable as needed.*

#### **C. Hugging Face Inference API (cloud-based)**
1. **Get your Hugging Face API token:**
   - Go to [https://huggingface.co/settings/tokens](https://huggingface.co/settings/tokens) and create a new token (read access is sufficient).
2. **Choose a model:**
   - You can use the default (`meta-llama/Llama-2-7b-chat-hf`) or any supported text generation model from [https://huggingface.co/models](https://huggingface.co/models).
3. **Run the system:**
   ```sh
   export LLM_PROVIDER=huggingface
   export HUGGINGFACE_API_TOKEN=hf_...yourtoken...
   export HUGGINGFACE_MODEL=meta-llama/Llama-2-7b-chat-hf  # or your preferred model
   docker-compose up --build
   ```

#### **D. Dummy Provider (for testing only)**
- No API keys or tokens needed. Just run:
  ```sh
  export LLM_PROVIDER=dummy
  docker-compose up --build
  ```

### 4. Access the Services
- **API Gateway:** http://localhost:8080
- **Camunda:** http://localhost:8081
- **OCR Service:** http://localhost:8090
- **LLM/Rule Engine:** http://localhost:8091
- **Validation Engine:** http://localhost:8092
- **Grafana:** http://localhost:3000
- **Ops UI:** http://localhost:3000 (if running)

### 5. Stopping and Cleaning Up
- Stop all services: `Ctrl+C` then `docker-compose down`
- Remove unused containers/images: `docker system prune -af`

### 6. Troubleshooting
- **Port conflicts:** Make sure nothing else is running on the listed ports.
- **Ollama not found:** Ensure you installed Ollama and that `ollama run llama2` is running in a separate terminal.
- **API key/token errors:** Double-check you copied the full key/token and set the correct environment variable.
- **Code changes:** Re-run `docker-compose up --build` to rebuild images.
- **Logs:** View logs for a service with `docker-compose logs <service-name>`.

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