package com.devicemonitoring.backend.diagnostic;

import com.devicemonitoring.backend.telemetry.SystemTelemetryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticEngine {
    private static final Logger logger = LoggerFactory.getLogger(DiagnosticEngine.class);

    /**
     * A deterministic, rule-based diagnostic engine.
     * This prepares the architecture for future AI integration.
     */
    public void analyzeTelemetry(SystemTelemetryEntity telemetry) {
        if (telemetry.getCpuUsage() != null && telemetry.getCpuUsage() > 90.0) {
            logger.warn("Diagnostic: High CPU detected on device {}", telemetry.getDeviceId());
            // In the future: trigger incident creation and action recommendation
        }

        if (telemetry.getMemoryUsagePercentage() != null && telemetry.getMemoryUsagePercentage() > 90.0) {
            logger.warn("Diagnostic: High Memory detected on device {}", telemetry.getDeviceId());
            // In the future: trigger incident creation and action recommendation
        }
    }
}
