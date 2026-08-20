package com.devicemonitoring.agent.scheduler;

import com.devicemonitoring.agent.collector.OshiCollector;
import com.devicemonitoring.agent.communication.BackendClient;
import com.devicemonitoring.agent.config.ResourceGuard;
import com.devicemonitoring.agent.model.ProcessTelemetry;
import com.devicemonitoring.agent.model.SystemTelemetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelemetryScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(TelemetryScheduler.class);
    
    private final OshiCollector oshiCollector;
    private final BackendClient backendClient;
    private final ResourceGuard resourceGuard;

    public TelemetryScheduler(OshiCollector oshiCollector, BackendClient backendClient, ResourceGuard resourceGuard) {
        this.oshiCollector = oshiCollector;
        this.backendClient = backendClient;
        this.resourceGuard = resourceGuard;
    }

    // Run every 10 seconds for system telemetry
    @Scheduled(fixedRateString = "${agent.monitoring.system-interval:10000}")
    public void collectAndSendSystemTelemetry() {
        if (!resourceGuard.isSafeToMonitor()) {
            logger.warn("Skipping system telemetry collection to protect resources.");
            return;
        }

        try {
            SystemTelemetry telemetry = oshiCollector.collectSystemTelemetry();
            if (telemetry != null) {
                // Track our own resource usage using the system stats roughly
                // In a real scenario we'd track just the java process
                resourceGuard.recordUsage(telemetry.getCpuUsage(), telemetry.getUsedMemory());
                
                backendClient.sendSystemTelemetry(telemetry);
                logger.info("Collected and sent system telemetry");
            }
        } catch (Exception e) {
            logger.error("Error collecting system telemetry", e);
        }
    }

    // Run every 30 seconds for process telemetry to save bandwidth/resources
    @Scheduled(fixedRateString = "${agent.monitoring.process-interval:30000}")
    public void collectAndSendProcessTelemetry() {
        if (!resourceGuard.isSafeToMonitor()) {
            return;
        }

        try {
            List<ProcessTelemetry> processes = oshiCollector.collectProcessTelemetry();
            if (!processes.isEmpty()) {
                backendClient.sendProcessTelemetry(processes);
                logger.info("Collected and sent {} processes telemetry", processes.size());
            }
        } catch (Exception e) {
            logger.error("Error collecting process telemetry", e);
        }
    }
}
