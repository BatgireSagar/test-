# OCR Service

This microservice extracts key fields from scanned or PDF documents using OCR technologies (Tesseract, AWS Textract, etc.).

## Features
- Accepts document uploads via REST API
- Extracts structured data (e.g., entity name, incorporation country)
- Adapter pattern for multiple OCR providers
- Designed for extensibility and modularity

## API
- `POST /extract` — Upload a document and receive extracted fields
  - **Request:** multipart/form-data with file
  - **Response Example:**
    ```json
    {
      "fields": {
        "entity_name": "Sample Entity",
        "incorporation_country": "Sample Country"
      }
    }
    ```

## Extensibility
- Add new OCR providers by implementing the `OcrProvider` interface in `ocr_service.py`.
- Configure provider selection via environment variable or config.

## Requirements
- Python 3.9+
- FastAPI
- Tesseract OCR (for local dev)

## Running Locally
```sh
pip install -r requirements.txt
uvicorn ocr_service:app --reload --host 0.0.0.0 --port 8090
```