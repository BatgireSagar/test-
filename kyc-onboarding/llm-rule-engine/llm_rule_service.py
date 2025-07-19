from fastapi import FastAPI, UploadFile, File, Form
from fastapi.responses import JSONResponse
from typing import Dict, Optional
import os

app = FastAPI(title="LLM/Rule Engine Service", description="Extracts onboarding requirements from PP documents using LLMs or rules.")

OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")

class RuleProvider:
    """
    Interface for rule extraction providers. Implement extract_requirements for new LLMs or rules engines.
    """
    def extract_requirements(self, file_bytes: bytes, context: Optional[str]) -> Dict[str, str]:
        raise NotImplementedError

class OpenAiRuleProvider(RuleProvider):
    def extract_requirements(self, file_bytes: bytes, context: Optional[str]) -> Dict[str, str]:
        import openai
        openai.api_key = OPENAI_API_KEY
        text = file_bytes.decode('utf-8', errors='ignore')
        prompt = f"Extract onboarding requirements for: {context}\n\n{text}"
        response = openai.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[{"role": "user", "content": prompt}]
        )
        return {"requirements": response.choices[0].message.content}

class DummyRuleProvider(RuleProvider):
    """
    Default provider returns dummy requirements. Replace with LLM or DMN logic.
    """
    def extract_requirements(self, file_bytes: bytes, context: Optional[str]) -> Dict[str, str]:
        return {
            "required_documents": "Prospectus, FATCA W8BEN-E, UBO details",
            "jurisdiction": "Cayman Islands",
            "product_type": "Derivatives"
        }

if OPENAI_API_KEY:
    rule_provider = OpenAiRuleProvider()
else:
    rule_provider = DummyRuleProvider()

@app.post("/extract-requirements")
async def extract_requirements(file: UploadFile = File(...), context: Optional[str] = Form(None)):
    """
    Extract onboarding requirements from the uploaded PP document and context using the configured provider.
    """
    file_bytes = await file.read()
    requirements = rule_provider.extract_requirements(file_bytes, context)
    return JSONResponse(content={"requirements": requirements})