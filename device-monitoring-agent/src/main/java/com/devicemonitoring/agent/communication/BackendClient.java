package com.devicemonitoring.agent.communication;

import com.devicemonitoring.agent.config.AgentConfig;
import com.devicemonitoring.agent.model.ProcessTelemetry;
import com.devicemonitoring.agent.model.SystemTelemetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BackendClient {
    private static final Logger logger = LoggerFactory.getLogger(BackendClient.class);
    
    private final WebClient webClient;
    private final AgentConfig config;

    public BackendClient(WebClient.Builder webClientBuilder, AgentConfig config) {
        this.webClient = webClientBuilder.baseUrl(config.getBackendUrl()).build();
        this.config = config;
    }

    public void sendSystemTelemetry(SystemTelemetry telemetry) {
        if (telemetry == null) return;
        
        webClient.post()
                .uri("/api/telemetry/system")
                .body(Mono.just(telemetry), SystemTelemetry.class)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(e -> logger.error("Failed to send system telemetry: {}", e.getMessage()))
                .subscribe();
    }

    public void sendProcessTelemetry(List<ProcessTelemetry> processes) {
        if (processes == null || processes.isEmpty()) return;

        webClient.post()
                .uri("/api/telemetry/processes")
                .body(Mono.just(processes), List.class)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(e -> logger.error("Failed to send process telemetry: {}", e.getMessage()))
                .subscribe();
    }
}
