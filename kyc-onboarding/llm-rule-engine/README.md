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