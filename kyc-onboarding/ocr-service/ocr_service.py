from fastapi import FastAPI, UploadFile, File
from fastapi.responses import JSONResponse
from typing import Dict
import pytesseract
from PIL import Image
import io

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
        image = Image.open(io.BytesIO(file_bytes))
        text = pytesseract.image_to_string(image)
        # Simple heuristic: look for 'Entity Name:' in text
        entity_name = ""
        for line in text.splitlines():
            if "entity name" in line.lower():
                entity_name = line.split(":", 1)[-1].strip()
        return {
            "raw_text": text,
            "entity_name": entity_name or "Not found"
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