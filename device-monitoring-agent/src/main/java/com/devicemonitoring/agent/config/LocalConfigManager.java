package com.devicemonitoring.agent.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class LocalConfigManager {

    private final ObjectMapper objectMapper;
    private LocalAgentConfig config;
    private final Path configPath;

    public LocalConfigManager() {
        this.objectMapper = new ObjectMapper();
        String userHome = System.getProperty("user.home");
        this.configPath = Paths.get(userHome, ".devicemonitor", "agent-config.json");
    }

    @PostConstruct
    public void init() {
        loadConfig();
    }

    public synchronized void loadConfig() {
        File configFile = configPath.toFile();
        if (configFile.exists()) {
            try {
                this.config = objectMapper.readValue(configFile, LocalAgentConfig.class);
                log.info("Loaded configuration from {}", configPath);
            } catch (IOException e) {
                log.error("Failed to read config file, generating default. Error: {}", e.getMessage());
                this.config = new LocalAgentConfig();
            }
        } else {
            log.info("No config file found at {}, generating new config.", configPath);
            this.config = new LocalAgentConfig();
        }
        
        config.ensureDeviceId();
        saveConfig();
    }

    public synchronized void saveConfig() {
        try {
            Files.createDirectories(configPath.getParent());
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(configPath.toFile(), this.config);
            log.info("Saved configuration to {}", configPath);
        } catch (IOException e) {
            log.error("Failed to save config file: {}", e.getMessage());
        }
    }

    public LocalAgentConfig getConfig() {
        return this.config;
    }
}
