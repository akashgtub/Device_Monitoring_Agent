package com.devicemonitoring.backend.incident;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Data
@Entity
@Table(name = "incidents")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String incidentId;

    @Column(nullable = false)
    private String deviceId;

    private String type;
    private String severity;
    private String status;

    @Column(nullable = false)
    private Instant detectedAt;

    private Instant resolvedAt;

    @Column(columnDefinition = "TEXT")
    private String summary;
}
