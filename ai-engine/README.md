# Device Monitoring AI Engine

This is the Python AI Engine Foundation for the Device Monitoring project.

## Purpose
This service receives selected telemetry from the Java Spring Boot backend and performs deterministic anomaly detection (using Isolation Forest) and preliminary rule-based diagnosis.

**Note:** This phase implements anomaly detection and preliminary rule-based diagnosis. RAG, GenAI, and agentic investigation are intentionally not implemented yet. 
This engine does **not** directly communicate with or control the monitored laptop.

## Architecture
- **Framework**: FastAPI
- **ML**: `scikit-learn` (Isolation Forest)
- **Validation**: Pydantic

## How to Run

1. **Create a virtual environment**:
   ```bash
   python -m venv venv
   ```
2. **Activate the virtual environment**:
   - Windows: `.\venv\Scripts\activate`
   - Linux/Mac: `source venv/bin/activate`
3. **Install dependencies**:
   ```bash
   pip install -r requirements.txt
   ```
4. **Start the server**:
   ```bash
   uvicorn app.main:app --reload --port 8000
   ```

## API Endpoints

- `GET /health` : Returns service health status.
- `POST /api/analyze/telemetry` : Accepts JSON telemetry data and returns an anomaly score and preliminary diagnosis.

### Example Request

```json
{
    "deviceId": "device-001",
    "timestamp": "2026-08-20T10:00:00Z",
    "cpuUsage": 82.4,
    "memoryUsage": 91.2,
    "diskUsage": 78.5,
    "temperature": 68.0,
    "processes": [
        {
            "pid": 1234,
            "name": "chrome.exe",
            "cpuUsage": 42.3,
            "memoryUsage": 35.4
        }
    ]
}
```

### Example Response

```json
{
    "deviceId": "device-001",
    "anomaly": {
        "detected": true,
        "score": 0.87,
        "severity": "HIGH"
    },
    "diagnosis": {
        "type": "RULE_BASED_PRELIMINARY_DIAGNOSIS",
        "category": "SOFTWARE",
        "probableCause": "High memory consumption by an application",
        "evidence": [
            "Memory usage is above threshold (91.2 > 90)"
        ]
    },
    "recommendedNextStep": "Review highly consuming processes"
}
```

## Current Limitations
- The model is a simple static Isolation Forest.
- Diagnoses are strictly rule-based (if-else logic).
- No LLMs are used for root cause analysis.
