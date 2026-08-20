# Project Architecture Rules: Device Monitoring

The project is strictly divided into THREE major layers with specific language responsibilities:

## 1. FRONTEND
**Technologies**: React + JavaScript + Vanilla CSS
**Rule**: Do NOT redesign or replace the existing frontend.

## 2. BACKEND + DEVICE MONITORING
**Technologies**: Java + Spring Boot (Backend), Java + OSHI (Local Monitoring Agent)
**Rule**: JAVA IS THE EXCLUSIVE BACKEND AND AGENT LANGUAGE.
- **Use Java for**: Spring Boot backend, REST APIs, WebSocket communication, Device registration, Device identity, User permissions, Monitoring configuration, Telemetry ingestion, Device management, Incident management, Diagnostic orchestration, Action management, Automation policy, User approval, Safe remediation, Verification, Audit history, Communication with the local monitoring agent.
- **Use Java + OSHI (Local Agent) for**: CPU monitoring, RAM monitoring, Disk monitoring, GPU information, Temperature/sensor information, Battery information, Network information, Running processes, Services, Operating-system information.

## 3. AI ENGINE
**Technologies**: Python (FastAPI, pandas, NumPy, scikit-learn, LLM/agent libraries)
**Rule**: PYTHON IS ONLY FOR THE AI ENGINE.
- **Use Python for**: Machine Learning, Anomaly Detection, Pattern Detection, Root Cause Classification, RAG, Embeddings, Vector Retrieval, GenAI / LLM processing, Agentic AI, AI-based investigation, AI recommendations.
- **Strict Constraint**: Do NOT move normal backend responsibilities into Python. Do NOT use Python for Device registration, User permissions, Main application backend, PostgreSQL business logic, Automation policy, or Device-agent management.
