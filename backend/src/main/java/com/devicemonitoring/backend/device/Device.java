package com.devicemonitoring.backend.device;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Data
@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String deviceId;

    private String deviceName;
    private String operatingSystem;
    private String osVersion;
    private String processor;
    private Long totalMemory;
    private String agentVersion;
    private String status;
    private String token; // For development auth

    private Instant lastSeen;
    
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
        lastSeen = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
