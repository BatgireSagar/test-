# LLM/Rule Engine Service

This microservice extracts onboarding requirements and business rules from Policy & Procedure (PP) documents using LLMs (e.g., GPT, Llama2) or structured logic (DMN).

## Features
- Accepts PP documents and context via REST API
- Extracts required documents, information, and rules for onboarding
- Supports both LLM-based and rules-based extraction
- Designed for extensibility and modularity

## API
- `POST /extract-requirements` — Upload a PP document and context, receive extracted requirements
  - **Request:** multipart/form-data with file and optional context
  - **Response Example:**
    ```json
    {
      "requirements": {
        "required_documents": "Prospectus, FATCA W8BEN-E, UBO details",
        "jurisdiction": "Cayman Islands",
        "product_type": "Derivatives"
      }
    }
    ```

## Extensibility
- Add new LLM providers or rule engines by implementing the `RuleProvider` interface in `llm_rule_service.py`.
- Configure provider selection via environment variable or config.

## Requirements
- Python 3.9+
- FastAPI
- (Optional) OpenAI, HuggingFace, or Llama2 client libraries

## Running Locally
```sh
pip install -r requirements.txt
uvicorn llm_rule_service:app --reload --host 0.0.0.0 --port 8091
```

## Configuring LLM Providers (OpenAI, Ollama, Hugging Face, etc.)

You can use different LLMs for the Rule Engine microservice by setting environment variables—**no code changes required**:

### 1. OpenAI (default)
- Set `LLM_PROVIDER=openai` (default) and `OPENAI_API_KEY=sk-...`.
- See above for how to get your OpenAI API key.

### 2. Ollama (local LLM)
- [Install Ollama](https://ollama.com/download) and pull a model (e.g., `ollama pull llama2`).
- Start Ollama: `ollama run llama2`
- Set `LLM_PROVIDER=ollama` and (optionally) `OLLAMA_API_URL=http://localhost:11434`.

### 3. Hugging Face Inference API
- Set `LLM_PROVIDER=huggingface`, `HUGGINGFACE_API_TOKEN=hf_...`, and (optionally) `HUGGINGFACE_MODEL=meta-llama/Llama-2-7b-chat-hf`.
- Get your Hugging Face token from [https://huggingface.co/settings/tokens](https://huggingface.co/settings/tokens).

### 4. Dummy Provider (for testing)
- Set `LLM_PROVIDER=dummy` to use a static/dummy response.

### Example (in your shell or docker-compose.yml):
```sh
export LLM_PROVIDER=ollama
export OLLAMA_API_URL=http://localhost:11434
# or for OpenAI:
# export LLM_PROVIDER=openai
# export OPENAI_API_KEY=sk-...
```

**No code changes are needed to switch LLMs—just set the environment variables!**

---