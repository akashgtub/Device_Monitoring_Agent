package com.devicemonitoring.agent.model;

import lombok.Data;

@Data
public class ActionRequest {
    private String id;
    private String deviceId;
    private Long incidentId;
    private String actionType;
    private String description;
    private String riskLevel;
    private boolean requiresConfirmation;
    private String status;
    private String requestedBy;
    private String actionParameters;
}
