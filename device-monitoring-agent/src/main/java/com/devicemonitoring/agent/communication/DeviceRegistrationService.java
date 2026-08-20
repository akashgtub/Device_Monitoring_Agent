package com.devicemonitoring.agent.communication;

import com.devicemonitoring.agent.config.LocalConfigManager;
import com.devicemonitoring.agent.model.DeviceRegistrationRequest;
import com.devicemonitoring.agent.model.DeviceRegistrationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import oshi.SystemInfo;
import reactor.core.publisher.Mono;

@Service
public class DeviceRegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceRegistrationService.class);

    private final WebClient webClient;
    private final LocalConfigManager configManager;

    public DeviceRegistrationService(WebClient.Builder webClientBuilder, LocalConfigManager configManager) {
        this.webClient = webClientBuilder.baseUrl(configManager.getConfig().getBackendUrl()).build();
        this.configManager = configManager;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void registerDevice() {
        SystemInfo si = new SystemInfo();
        
        DeviceRegistrationRequest request = new DeviceRegistrationRequest();
        request.setDeviceId(configManager.getConfig().getDeviceId());
        
        String hostname = si.getOperatingSystem().getNetworkParams().getHostName();
        request.setDeviceName(hostname != null ? hostname : "Unknown-PC");
        request.setOperatingSystem(si.getOperatingSystem().getFamily());
        request.setOsVersion(si.getOperatingSystem().getVersionInfo().getVersion());
        request.setAgentVersion("0.1.0"); // Fixed for now

        logger.info("Registering device: {}", request.getDeviceId());

        webClient.post()
                .uri("/api/devices/register")
                .body(Mono.just(request), DeviceRegistrationRequest.class)
                .retrieve()
                .bodyToMono(DeviceRegistrationResponse.class)
                .doOnSuccess(response -> {
                    logger.info("Successfully registered with backend.");
                    if (response != null && response.getToken() != null) {
                        configManager.getConfig().setToken(response.getToken());
                        configManager.saveConfig();
                    }
                })
                .doOnError(e -> logger.error("Failed to register device: {}", e.getMessage()))
                .subscribe();
    }
}
