package com.devicemonitoring.agent.config;

import lombok.Data;
import java.util.UUID;

@Data
public class LocalAgentConfig {
    private String deviceId;
    private String backendUrl = "http://localhost:8080";
    private String token; // For development
    
    // Permissions
    private boolean hardwareMonitoring = true;
    private boolean processMonitoring = true;
    private boolean softwareMonitoring = true;
    private boolean systemEventMonitoring = true;
    private boolean diagnosticMonitoring = true;
    private boolean automationPermission = false;
    
    public void ensureDeviceId() {
        if (deviceId == null || deviceId.isBlank()) {
            deviceId = UUID.randomUUID().toString();
        }
    }
}
