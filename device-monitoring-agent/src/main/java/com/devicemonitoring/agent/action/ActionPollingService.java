package com.devicemonitoring.agent.action;

import com.devicemonitoring.agent.config.LocalConfigManager;
import com.devicemonitoring.agent.model.ActionRequest;
import com.devicemonitoring.agent.model.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ActionPollingService {
    
    private static final Logger logger = LoggerFactory.getLogger(ActionPollingService.class);
    
    private final WebClient webClient;
    private final LocalConfigManager configManager;
    private final ActionValidator actionValidator;
    private final ActionExecutor actionExecutor;

    public ActionPollingService(WebClient.Builder webClientBuilder, 
                                LocalConfigManager configManager,
                                ActionValidator actionValidator,
                                ActionExecutor actionExecutor) {
        this.webClient = webClientBuilder.baseUrl(configManager.getConfig().getBackendUrl()).build();
        this.configManager = configManager;
        this.actionValidator = actionValidator;
        this.actionExecutor = actionExecutor;
    }

    // Poll every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void pollForActions() {
        String deviceId = configManager.getConfig().getDeviceId();
        if (deviceId == null) return;
        
        // Skip polling if automation is disabled locally to save bandwidth
        if (!configManager.getConfig().isAutomationPermission()) {
            return;
        }

        webClient.get()
                .uri("/api/actions/device/{deviceId}/pending", deviceId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ActionRequest>>() {})
                .doOnSuccess(actions -> {
                    if (actions != null && !actions.isEmpty()) {
                        for (ActionRequest action : actions) {
                            processAction(action);
                        }
                    }
                })
                .doOnError(e -> logger.debug("Error polling for actions: {}", e.getMessage()))
                .subscribe();
    }
    
    private void processAction(ActionRequest action) {
        logger.info("Received action request: {}", action.getActionType());
        
        ActionResult result;
        if (actionValidator.validate(action)) {
            result = actionExecutor.execute(action);
        } else {
            result = new ActionResult("FAILED", "Action rejected by local validator (permission or allowlist).");
        }
        
        reportResult(action.getId(), result);
    }
    
    private void reportResult(String actionId, ActionResult result) {
        webClient.post()
                .uri("/api/actions/{actionId}/result", actionId)
                .body(Mono.just(result), ActionResult.class)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(v -> logger.info("Successfully reported result for action {}", actionId))
                .doOnError(e -> logger.error("Failed to report action result: {}", e.getMessage()))
                .subscribe();
    }
}
