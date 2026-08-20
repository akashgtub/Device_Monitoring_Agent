package com.devicemonitoring.backend.telemetry;

import lombok.Data;
import java.time.Instant;

@Data
public class SystemTelemetryDTO {
    private String deviceId;
    private Instant timestamp;
    private Double cpuUsage;
    private Double memoryUsagePercentage;
    private Double storageUsagePercentage;
    private Double systemTemperature;
    private Double batteryPercentage;
}
