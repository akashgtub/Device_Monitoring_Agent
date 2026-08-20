from app.models.telemetry import TelemetryInput
from typing import Dict, Any

class PreprocessingService:
    @staticmethod
    def extract_features(telemetry: TelemetryInput) -> Dict[str, float]:
        """
        Extract and normalize numerical features from the raw telemetry.
        Missing temperature is handled by imputing a sensible default (e.g., 40.0) 
        if None, to keep the model inputs consistent.
        """
        temp = telemetry.temperature if telemetry.temperature is not None else 40.0
        
        return {
            "cpuUsage": telemetry.cpuUsage,
            "memoryUsage": telemetry.memoryUsage,
            "diskUsage": telemetry.diskUsage,
            "temperature": temp
        }
