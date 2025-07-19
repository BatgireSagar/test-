# Validation Engine Service

This microservice validates client-submitted data and documents against onboarding requirements extracted from Policy & Procedure (PP) documents.

## Features
- Accepts client data and requirements via REST API
- Compares submitted info to required fields and documents
- Returns structured validation reports (validated, missing, invalid, mismatched)
- Designed for extensibility and modularity

## API
- `POST /validate` — Submit client data and requirements, receive validation report
  - **Request Example:**
    ```json
    {
      "clientData": {
        "entity_name": "Sample Entity",
        "incorporation_country": "Sample Country"
      },
      "requirements": {
        "required_documents": "Prospectus, FATCA W8BEN-E, UBO details"
      }
    }
    ```
  - **Response Example:**
    ```json
    {
      "status": "manual_review_required",
      "validated": ["Entity Name", "Incorporation Country"],
      "missing": ["UBO Declaration"],
      "invalid": ["Expired Passport"],
      "mismatched": ["Form Type: W8BEN instead of W8BEN-E"],
      "auditTrail": [
        {"step": "validation", "result": "manual_review_required"}
      ]
    }
    ```

## Extensibility
- Add new validation rules or product/jurisdiction logic by extending the validation service
- Modular rule engine for easy updates

## Requirements
- Java 17+
- Spring Boot 3.2+
- Maven 3.8+

## Running Locally
```sh
cd validation-engine
mvn clean install
mvn spring-boot:run
```