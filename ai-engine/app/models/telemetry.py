from pydantic import BaseModel, Field
from typing import List, Optional
from datetime import datetime

class ProcessInfo(BaseModel):
    pid: int = Field(..., description="Process ID")
    name: str = Field(..., description="Process Name")
    cpuUsage: float = Field(..., ge=0, le=100, description="Process CPU usage percentage")
    memoryUsage: float = Field(..., ge=0, description="Process memory usage in MB")

class TelemetryInput(BaseModel):
    deviceId: str = Field(..., description="Unique device identifier")
    timestamp: datetime = Field(..., description="Timestamp of the telemetry")
    cpuUsage: float = Field(..., ge=0, le=100, description="System CPU usage percentage")
    memoryUsage: float = Field(..., ge=0, le=100, description="System memory usage percentage")
    diskUsage: float = Field(..., ge=0, le=100, description="System disk usage percentage")
    temperature: Optional[float] = Field(None, description="System temperature in Celsius")
    processes: Optional[List[ProcessInfo]] = Field(default_factory=list, description="List of running processes")
