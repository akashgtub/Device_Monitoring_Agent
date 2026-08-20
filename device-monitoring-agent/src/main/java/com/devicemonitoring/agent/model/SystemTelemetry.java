package com.devicemonitoring.agent.model;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class SystemTelemetry {
    private String deviceId;
    private Instant timestamp;
    
    // CPU
    private String cpuName;
    private double cpuUsage;
    private long cpuFrequency;
    private int logicalProcessors;
    private int physicalProcessors;
    
    // Memory
    private long totalMemory;
    private long usedMemory;
    private long availableMemory;
    private double memoryUsagePercentage;
    
    // Storage (Aggregate or Main OS drive for simplicity in telemetry)
    private long totalStorage;
    private long usedStorage;
    private long freeStorage;
    private double storageUsagePercentage;
    
    // Temperature
    private double systemTemperature;
    
    // Battery
    private double batteryPercentage;
    private boolean isCharging;
    
    // Network
    private long bytesSent;
    private long bytesReceived;
    
    // System info
    private String operatingSystem;
    private String osVersion;
    private String hostname;
    private long systemUptime;
}
