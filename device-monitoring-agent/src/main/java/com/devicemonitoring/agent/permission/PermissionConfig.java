package com.devicemonitoring.agent.permission;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class PermissionConfig {
    
    private final com.devicemonitoring.agent.config.LocalConfigManager configManager;
    
    public PermissionConfig(com.devicemonitoring.agent.config.LocalConfigManager configManager) {
        this.configManager = configManager;
    }
    
    public boolean isHardwareMonitoring() {
        return configManager.getConfig().isHardwareMonitoring();
    }
    
    public boolean isProcessMonitoring() {
        return configManager.getConfig().isProcessMonitoring();
    }
    
    public boolean isSoftwareMonitoring() {
        return configManager.getConfig().isSoftwareMonitoring();
    }
    
    public boolean isSystemEventMonitoring() {
        return configManager.getConfig().isSystemEventMonitoring();
    }
    
    public boolean isDiagnosticMonitoring() {
        return configManager.getConfig().isDiagnosticMonitoring();
    }
    
    public boolean isAutomationPermission() {
        return configManager.getConfig().isAutomationPermission();
    }
}
