package com.devicemonitoring.agent.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AgentConfig {
    
    private final LocalConfigManager configManager;
    
    public AgentConfig(LocalConfigManager configManager) {
        this.configManager = configManager;
    }
    
    public String getDeviceId() {
        return configManager.getConfig().getDeviceId();
    }
    
    public String getBackendUrl() {
        return configManager.getConfig().getBackendUrl();
    }
}
