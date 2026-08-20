package com.devicemonitoring.agent.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ResourceGuard ensures the monitoring agent does not consume too many system resources.
 * Future enhancement: if CPU/Memory crosses a threshold, reduce collection frequency.
 */
@Component
public class ResourceGuard {
    private static final Logger logger = LoggerFactory.getLogger(ResourceGuard.class);
    
    // Example limits
    private static final double MAX_AGENT_CPU = 5.0; // %
    private static final long MAX_AGENT_MEMORY_MB = 250;
    
    public boolean isSafeToMonitor() {
        // In a real scenario, this uses JMX or OSHI to check its own PID resources.
        // For this foundation, we just return true.
        return true;
    }
    
    public void recordUsage(double cpuPercent, long memoryBytes) {
        if (cpuPercent > MAX_AGENT_CPU) {
            logger.warn("Agent CPU usage is high: {}%", String.format("%.2f", cpuPercent));
        }
        long memoryMb = memoryBytes / (1024 * 1024);
        if (memoryMb > MAX_AGENT_MEMORY_MB) {
            logger.warn("Agent Memory usage is high: {} MB", memoryMb);
        }
    }
}
