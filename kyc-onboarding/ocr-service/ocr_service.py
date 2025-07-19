from fastapi import FastAPI, UploadFile, File
from fastapi.responses import JSONResponse
from typing import Dict

app = FastAPI(title="OCR Service", description="Extracts key fields from documents using OCR.")

class OcrProvider:
    """
    Interface for OCR providers. Implement extract_fields to support new OCR backends.
    """
    def extract_fields(self, file_bytes: bytes) -> Dict[str, str]:
        raise NotImplementedError

class TesseractOcrProvider(OcrProvider):
    """
    Default OCR provider using Tesseract. Replace or extend for AWS Textract, etc.
    """
    def extract_fields(self, file_bytes: bytes) -> Dict[str, str]:
        # TODO: Implement real OCR extraction using pytesseract
        # For now, return dummy data
        return {
            "entity_name": "Sample Entity",
            "incorporation_country": "Sample Country"
        }

# To add a new provider, implement OcrProvider and set ocr_provider accordingly.
ocr_provider = TesseractOcrProvider()

@app.post("/extract")
async def extract(file: UploadFile = File(...)):
    """
    Extract key fields from the uploaded document using the configured OCR provider.
    """
    file_bytes = await file.read()
    fields = ocr_provider.extract_fields(file_bytes)
    return JSONResponse(content={"fields": fields})