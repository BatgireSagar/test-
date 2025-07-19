# KYC Onboarding BPMN Workflow Overview

This document describes the Camunda BPMN process for the KYC onboarding automation system.

## Process Steps

1. **Start Event (Document Received)**
   - Triggered when a client submits onboarding documents.

2. **Document Parsing (Service Task)**
   - Calls the OCR service to extract key fields from submitted documents.
   - Camunda external task topic: `documentParsing`

3. **PP Rule Extraction (Service Task)**
   - Calls the LLM/Rule Engine to extract onboarding requirements from the latest Policy & Procedure document.
   - Camunda external task topic: `ppRuleExtraction`

4. **Validation (Service Task)**
   - Calls the Validation Engine to compare client data against requirements.
   - Camunda external task topic: `validation`

5. **Manual Review Needed? (Gateway)**
   - Checks if any exceptions or high-risk indicators require human review.

6. **Manual Review (User Task)**
   - Ops Analyst reviews flagged cases and provides input.

7. **Compliance Review (User Task)**
   - Compliance Officer reviews and approves/rejects onboarding for complex or high-risk clients.

8. **End Event (Onboard / Reject / Pending Info)**
   - Final decision is recorded and communicated to the client.

## Integration Points
- Each service task is implemented as a Camunda external task, allowing microservices to subscribe and process work items.
- User tasks are handled via Camunda Tasklist or a custom Ops UI.

## Extensibility
- Add new service/user tasks as business requirements evolve.
- Update DMN tables for new rules or jurisdictions.