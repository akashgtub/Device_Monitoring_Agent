from app.models.telemetry import TelemetryInput
from app.models.diagnosis import DiagnosisResult
from typing import Optional

class DiagnosisService:
    @staticmethod
    def diagnose(telemetry: TelemetryInput) -> Optional[DiagnosisResult]:
        """
        Rule-based preliminary diagnosis placeholder.
        No LLMs or complex AI are used here yet.
        """
        evidence = []
        
        # 1. High Memory Check
        if telemetry.memoryUsage > 90.0:
            evidence.append(f"Memory usage is critical ({telemetry.memoryUsage}%)")
            # Check processes for memory hogs
            if telemetry.processes:
                for p in telemetry.processes:
                    if p.memoryUsage > 500.0: # simplistic threshold in MB
                        evidence.append(f"Process '{p.name}' is consuming significant memory ({p.memoryUsage} MB)")
            
            if len(evidence) > 1:
                return DiagnosisResult(
                    category="SOFTWARE",
                    probableCause="High memory consumption by an application",
                    evidence=evidence
                )
                
        # 2. Thermal Check
        evidence = []
        if telemetry.temperature and telemetry.temperature > 80.0:
            evidence.append(f"Temperature is critical ({telemetry.temperature}°C)")
            if telemetry.cpuUsage > 85.0:
                evidence.append(f"CPU usage is high ({telemetry.cpuUsage}%)")
                return DiagnosisResult(
                    category="THERMAL",
                    probableCause="High system load associated with elevated temperature",
                    evidence=evidence
                )
            else:
                return DiagnosisResult(
                    category="THERMAL",
                    probableCause="Elevated temperature with normal CPU load (Check cooling system)",
                    evidence=evidence
                )
                
        # 3. CPU Check
        evidence = []
        if telemetry.cpuUsage > 90.0:
            evidence.append(f"CPU usage is critical ({telemetry.cpuUsage}%)")
            return DiagnosisResult(
                category="SOFTWARE",
                probableCause="High CPU load",
                evidence=evidence
            )
            
        # Return None if no clear diagnosis is found by rules
        return None
