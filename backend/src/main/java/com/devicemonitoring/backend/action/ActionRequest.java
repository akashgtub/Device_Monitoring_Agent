package com.devicemonitoring.backend.action;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Data
@Entity
@Table(name = "action_requests")
public class ActionRequest {
    @Id
    private String id; // UUID

    @Column(nullable = false)
    private String deviceId;

    private Long incidentId;

    @Column(nullable = false)
    private String actionType; // e.g., "DIAGNOSTIC", "RESTART_AGENT", "RESTART_APP"

    private String description;

    private String riskLevel; // LOW, MEDIUM, HIGH

    private boolean requiresConfirmation;

    @Column(nullable = false)
    private String status; // REQUESTED, APPROVED, REJECTED, EXECUTING, SUCCESS, FAILED, CANCELLED

    private String requestedBy;
    
    private String actionParameters; // JSON string of parameters if needed

    private String result; // The outcome details

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant executedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        if (id == null) {
            id = java.util.UUID.randomUUID().toString();
        }
    }
}
