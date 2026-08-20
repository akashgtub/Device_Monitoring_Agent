from pydantic import BaseModel
from typing import Dict, Any

class AnomalyResult(BaseModel):
    detected: bool
    score: float
    severity: str
    features: Dict[str, Any]
