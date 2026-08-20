package com.devicemonitoring.agent.action;

import com.devicemonitoring.agent.config.LocalConfigManager;
import com.devicemonitoring.agent.model.ActionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ActionValidator {
    
    private static final Logger logger = LoggerFactory.getLogger(ActionValidator.class);
    
    private final LocalConfigManager configManager;
    
    private static final Set<String> ALLOWED_ACTIONS = Set.of(
            "REFRESH_MONITORING_DATA",
            "RUN_SYSTEM_DIAGNOSTIC",
            "RESTART_AGENT",
            "RESTART_APPLICATION",
            "RESTART_NON_CRITICAL_SERVICE"
    );

    public ActionValidator(LocalConfigManager configManager) {
        this.configManager = configManager;
    }

    public boolean validate(ActionRequest request) {
        if (!configManager.getConfig().isAutomationPermission()) {
            logger.warn("Action rejected locally: Automation permission is disabled.");
            return false;
        }
        
        if (!ALLOWED_ACTIONS.contains(request.getActionType())) {
            logger.warn("Action rejected locally: Action type {} is not in the allowlist.", request.getActionType());
            return false;
        }
        
        return true;
    }
}
