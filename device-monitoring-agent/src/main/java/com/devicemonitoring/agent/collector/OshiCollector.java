package com.devicemonitoring.agent.collector;

import com.devicemonitoring.agent.config.AgentConfig;
import com.devicemonitoring.agent.model.ProcessTelemetry;
import com.devicemonitoring.agent.model.SystemTelemetry;
import com.devicemonitoring.agent.permission.PermissionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class OshiCollector {

    private static final Logger logger = LoggerFactory.getLogger(OshiCollector.class);

    private final SystemInfo systemInfo;
    private final AgentConfig agentConfig;
    private final PermissionConfig permissionConfig;

    private long[] prevTicks;

    public OshiCollector(AgentConfig agentConfig, PermissionConfig permissionConfig) {
        this.systemInfo = new SystemInfo();
        this.agentConfig = agentConfig;
        this.permissionConfig = permissionConfig;
        this.prevTicks = systemInfo.getHardware().getProcessor().getSystemCpuLoadTicks();
    }

    public SystemTelemetry collectSystemTelemetry() {
        if (!permissionConfig.isHardwareMonitoring()) {
            return null;
        }

        HardwareAbstractionLayer hal = systemInfo.getHardware();
        OperatingSystem os = systemInfo.getOperatingSystem();

        // CPU
        CentralProcessor processor = hal.getProcessor();
        double cpuUsage = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        prevTicks = processor.getSystemCpuLoadTicks();
        
        long cpuFrequency = processor.getProcessorIdentifier().getVendorFreq();

        // Memory
        GlobalMemory memory = hal.getMemory();
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        long usedMemory = totalMemory - availableMemory;
        double memoryUsagePercentage = (double) usedMemory / totalMemory * 100;

        // Storage - simplified aggregate
        long totalStorage = 0;
        long freeStorage = 0;
        var fs = os.getFileSystem();
        for (var fstore : fs.getFileStores()) {
            totalStorage += fstore.getTotalSpace();
            freeStorage += fstore.getUsableSpace();
        }
        long usedStorage = totalStorage - freeStorage;
        double storageUsagePercentage = totalStorage > 0 ? (double) usedStorage / totalStorage * 100 : 0;

        // Network
        long bytesSent = 0;
        long bytesReceived = 0;
        for (NetworkIF net : hal.getNetworkIFs()) {
            net.updateAttributes();
            bytesSent += net.getBytesSent();
            bytesReceived += net.getBytesRecv();
        }

        return SystemTelemetry.builder()
                .deviceId(agentConfig.getDeviceId())
                .timestamp(Instant.now())
                .cpuName(processor.getProcessorIdentifier().getName())
                .cpuUsage(cpuUsage)
                .cpuFrequency(cpuFrequency)
                .logicalProcessors(processor.getLogicalProcessorCount())
                .physicalProcessors(processor.getPhysicalProcessorCount())
                .totalMemory(totalMemory)
                .usedMemory(usedMemory)
                .availableMemory(availableMemory)
                .memoryUsagePercentage(memoryUsagePercentage)
                .totalStorage(totalStorage)
                .usedStorage(usedStorage)
                .freeStorage(freeStorage)
                .storageUsagePercentage(storageUsagePercentage)
                .systemTemperature(0.0) // OSHI temperature often requires admin rights, default 0
                .batteryPercentage(hal.getPowerSources().isEmpty() ? 100.0 : hal.getPowerSources().get(0).getRemainingCapacityPercent() * 100)
                .isCharging(!hal.getPowerSources().isEmpty() && hal.getPowerSources().get(0).isCharging())
                .bytesSent(bytesSent)
                .bytesReceived(bytesReceived)
                .operatingSystem(os.getFamily())
                .osVersion(os.getVersionInfo().getVersion())
                .hostname(os.getNetworkParams().getHostName())
                .systemUptime(os.getSystemUptime())
                .build();
    }

    public List<ProcessTelemetry> collectProcessTelemetry() {
        if (!permissionConfig.isProcessMonitoring()) {
            return new ArrayList<>();
        }

        OperatingSystem os = systemInfo.getOperatingSystem();
        List<OSProcess> processes = os.getProcesses(null, OperatingSystem.ProcessSorting.CPU_DESC, 15);
        List<ProcessTelemetry> telemetryList = new ArrayList<>();

        for (OSProcess p : processes) {
            telemetryList.add(ProcessTelemetry.builder()
                    .deviceId(agentConfig.getDeviceId())
                    .timestamp(Instant.now())
                    .processId(p.getProcessID())
                    .processName(p.getName())
                    .cpuUsage(100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime())
                    .memoryUsage(p.getResidentSetSize())
                    .status(p.getState().name())
                    .build());
        }

        return telemetryList;
    }
}
