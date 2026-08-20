from fastapi import APIRouter, HTTPException
from app.models.telemetry import TelemetryInput
from app.models.diagnosis import AIAnalysisResponse
from app.services.preprocessing import PreprocessingService
from app.services.anomaly_service import AnomalyService
from app.services.diagnosis_service import DiagnosisService

router = APIRouter()
anomaly_service = AnomalyService()

@router.post("/telemetry", response_model=AIAnalysisResponse)
async def analyze_telemetry(telemetry: TelemetryInput):
    try:
        # 1. Preprocess
        features = PreprocessingService.extract_features(telemetry)
        
        # 2. Anomaly Detection
        anomaly_result = anomaly_service.analyze(features)
        
        # 3. Rule-based Diagnosis
        diagnosis_result = DiagnosisService.diagnose(telemetry)
        
        # 4. Recommendation Logic
        next_step = "No action required"
        if anomaly_result.detected:
            if diagnosis_result:
                next_step = f"Investigate {diagnosis_result.category} issues based on evidence."
            else:
                next_step = "Collect detailed process telemetry or perform general health check."
                
        return AIAnalysisResponse(
            deviceId=telemetry.deviceId,
            anomaly=anomaly_result,
            diagnosis=diagnosis_result,
            recommendedNextStep=next_step
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
