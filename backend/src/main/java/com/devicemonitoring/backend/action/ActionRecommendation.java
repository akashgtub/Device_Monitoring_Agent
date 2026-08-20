package com.devicemonitoring.backend.action;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "action_recommendations")
public class ActionRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String actionId;

    @Column(nullable = false)
    private String incidentId;

    private String actionType;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String riskLevel;
    
    private boolean requiresUserApproval = true;
    
    private String status;
}
