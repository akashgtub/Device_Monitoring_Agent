from fastapi.testclient import TestClient
from app.main import app
import pytest

client = TestClient(app)

def test_health_check():
    response = client.get("/health")
    assert response.status_code == 200
    assert response.json() == {"status": "UP", "service": "device-monitoring-ai"}

def test_analyze_telemetry_normal():
    payload = {
        "deviceId": "test-device",
        "timestamp": "2026-08-20T10:00:00Z",
        "cpuUsage": 20.0,
        "memoryUsage": 40.0,
        "diskUsage": 30.0,
        "temperature": 45.0
    }
    response = client.post("/api/analyze/telemetry", json=payload)
    assert response.status_code == 200
    data = response.json()
    assert data["deviceId"] == "test-device"
    assert data["anomaly"]["detected"] == False
    assert data["diagnosis"] is None

def test_analyze_telemetry_abnormal():
    payload = {
        "deviceId": "test-device",
        "timestamp": "2026-08-20T10:00:00Z",
        "cpuUsage": 95.0,
        "memoryUsage": 94.0,
        "diskUsage": 90.0,
        "temperature": 82.0
    }
    response = client.post("/api/analyze/telemetry", json=payload)
    assert response.status_code == 200
    data = response.json()
    assert data["anomaly"]["detected"] == True
    assert data["diagnosis"]["category"] == "SOFTWARE" # Memory > 90 hits first in the simplistic rule engine

def test_invalid_telemetry():
    payload = {
        "deviceId": "test-device",
        "timestamp": "2026-08-20T10:00:00Z",
        "cpuUsage": 150.0, # Invalid > 100
        "memoryUsage": 40.0,
        "diskUsage": 30.0
    }
    response = client.post("/api/analyze/telemetry", json=payload)
    assert response.status_code == 422 # Unprocessable Entity from Pydantic
