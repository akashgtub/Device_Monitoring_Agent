from pydantic import BaseModel
from typing import List, Optional
from app.models.anomaly import AnomalyResult

class DiagnosisResult(BaseModel):
    type: str = "RULE_BASED_PRELIMINARY_DIAGNOSIS"
    category: str
    probableCause: str
    evidence: List[str]

class AIAnalysisResponse(BaseModel):
    deviceId: str
    anomaly: AnomalyResult
    diagnosis: Optional[DiagnosisResult] = None
    recommendedNextStep: str
