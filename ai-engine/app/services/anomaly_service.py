from app.ml.isolation_forest import AnomalyDetector
from app.models.anomaly import AnomalyResult

class AnomalyService:
    def __init__(self):
        self.detector = AnomalyDetector()

    def analyze(self, features: dict) -> AnomalyResult:
        is_anomaly, score = self.detector.detect(features)
        
        # Determine severity based on a simple threshold for now
        severity = "LOW"
        if is_anomaly:
            severity = "HIGH" if score > 0.8 else "MEDIUM"
            
        return AnomalyResult(
            detected=is_anomaly,
            score=round(score, 2),
            severity=severity,
            features=features
        )
