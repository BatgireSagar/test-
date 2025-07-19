from fastapi import FastAPI, UploadFile, File, Form
from fastapi.responses import JSONResponse
from typing import Dict, Optional
import os
import requests

app = FastAPI(title="LLM/Rule Engine Service", description="Extracts onboarding requirements from PP documents using LLMs or rules.")

LLM_PROVIDER = os.getenv("LLM_PROVIDER", "openai").lower()  # openai, ollama, huggingface, dummy
OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")
OLLAMA_API_URL = os.getenv("OLLAMA_API_URL", "http://localhost:11434")
HUGGINGFACE_API_TOKEN = os.getenv("HUGGINGFACE_API_TOKEN")
HUGGINGFACE_MODEL = os.getenv("HUGGINGFACE_MODEL", "meta-llama/Llama-2-7b-chat-hf")

class RuleProvider:
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

class OllamaRuleProvider(RuleProvider):
    def extract_requirements(self, file_bytes: bytes, context: Optional[str]) -> Dict[str, str]:
        text = file_bytes.decode('utf-8', errors='ignore')
        prompt = f"Extract onboarding requirements for: {context}\n\n{text}"
        data = {"model": "llama2", "prompt": prompt}
        resp = requests.post(f"{OLLAMA_API_URL}/api/generate", json=data)
        resp.raise_for_status()
        return {"requirements": resp.json().get("response", "")}

class HuggingFaceRuleProvider(RuleProvider):
    def extract_requirements(self, file_bytes: bytes, context: Optional[str]) -> Dict[str, str]:
        text = file_bytes.decode('utf-8', errors='ignore')
        prompt = f"Extract onboarding requirements for: {context}\n\n{text}"
        headers = {"Authorization": f"Bearer {HUGGINGFACE_API_TOKEN}"}
        payload = {"inputs": prompt}
        resp = requests.post(
            f"https://api-inference.huggingface.co/models/{HUGGINGFACE_MODEL}",
            headers=headers, json=payload
        )
        resp.raise_for_status()
        result = resp.json()
        if isinstance(result, list) and len(result) > 0 and 'generated_text' in result[0]:
            return {"requirements": result[0]['generated_text']}
        return {"requirements": str(result)}

class DummyRuleProvider(RuleProvider):
    def extract_requirements(self, file_bytes: bytes, context: Optional[str]) -> Dict[str, str]:
        return {
            "required_documents": "Prospectus, FATCA W8BEN-E, UBO details",
            "jurisdiction": "Cayman Islands",
            "product_type": "Derivatives"
        }

# Provider selection logic
if LLM_PROVIDER == "openai" and OPENAI_API_KEY:
    rule_provider = OpenAiRuleProvider()
elif LLM_PROVIDER == "ollama":
    rule_provider = OllamaRuleProvider()
elif LLM_PROVIDER == "huggingface" and HUGGINGFACE_API_TOKEN:
    rule_provider = HuggingFaceRuleProvider()
else:
    rule_provider = DummyRuleProvider()

@app.post("/extract-requirements")
async def extract_requirements(file: UploadFile = File(...), context: Optional[str] = Form(None)):
    file_bytes = await file.read()
    requirements = rule_provider.extract_requirements(file_bytes, context)
    return JSONResponse(content={"requirements": requirements})