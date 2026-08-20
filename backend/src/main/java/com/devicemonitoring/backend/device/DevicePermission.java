package com.devicemonitoring.backend.device;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Data
@Entity
@Table(name = "device_permissions")
public class DevicePermission {
    @Id
    private String deviceId;

    private boolean hardwareMonitoring = true;
    private boolean processMonitoring = true;
    private boolean softwareMonitoring = true;
    private boolean systemEventMonitoring = true;
    private boolean diagnosticMonitoring = true;
    private boolean automationPermission = false;

    private Instant updatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
