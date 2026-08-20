package com.devicemonitoring.backend.telemetry;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Data
@Entity
@Table(name = "system_telemetry")
public class SystemTelemetryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String deviceId;

    @Column(nullable = false)
    private Instant timestamp;

    private Double cpuUsage;
    private Double memoryUsagePercentage;
    private Double storageUsagePercentage;
    private Double systemTemperature;
    private Double batteryPercentage;
}
