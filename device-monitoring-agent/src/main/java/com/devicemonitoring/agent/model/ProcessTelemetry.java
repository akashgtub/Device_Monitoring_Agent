package com.devicemonitoring.agent.model;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class ProcessTelemetry {
    private String deviceId;
    private Instant timestamp;
    
    private int processId;
    private String processName;
    private double cpuUsage;
    private long memoryUsage;
    private String status;
}
