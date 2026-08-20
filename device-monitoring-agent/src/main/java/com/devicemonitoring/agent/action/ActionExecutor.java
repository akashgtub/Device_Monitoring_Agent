package com.devicemonitoring.agent.action;

import com.devicemonitoring.agent.model.ActionRequest;
import com.devicemonitoring.agent.model.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActionExecutor {
    
    private static final Logger logger = LoggerFactory.getLogger(ActionExecutor.class);

    public ActionResult execute(ActionRequest request) {
        logger.info("Executing safe action: {}", request.getActionType());
        
        try {
            switch (request.getActionType()) {
                case "RUN_SYSTEM_DIAGNOSTIC":
                    return runDiagnostic(request);
                case "REFRESH_MONITORING_DATA":
                    return refreshMonitoringData(request);
                default:
                    return new ActionResult("FAILED", "Action execution not implemented for " + request.getActionType());
            }
        } catch (Exception e) {
            logger.error("Error executing action", e);
            return new ActionResult("FAILED", "Exception occurred: " + e.getMessage());
        }
    }
    
    private ActionResult runDiagnostic(ActionRequest request) {
        // Mocking a safe diagnostic run
        logger.info("Running system diagnostic (mocked)...");
        return new ActionResult("SUCCESS", "Diagnostic completed safely. System is nominal.");
    }
    
    private ActionResult refreshMonitoringData(ActionRequest request) {
        logger.info("Refreshing monitoring data...");
        // This would typically trigger an immediate collection. We will just mock success.
        return new ActionResult("SUCCESS", "Monitoring data refresh triggered.");
    }
}
