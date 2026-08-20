package com.devicemonitoring.backend.telemetry;

import com.devicemonitoring.backend.diagnostic.DiagnosticEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private static final Logger logger = LoggerFactory.getLogger(TelemetryController.class);

    private final TelemetryRepository telemetryRepository;
    private final DiagnosticEngine diagnosticEngine;

    public TelemetryController(TelemetryRepository telemetryRepository, DiagnosticEngine diagnosticEngine) {
        this.telemetryRepository = telemetryRepository;
        this.diagnosticEngine = diagnosticEngine;
    }

    @PostMapping("/system")
    public ResponseEntity<Void> receiveSystemTelemetry(@RequestBody SystemTelemetryDTO dto) {
        if (dto.getDeviceId() == null || dto.getTimestamp() == null) {
            return ResponseEntity.badRequest().build();
        }

        SystemTelemetryEntity entity = new SystemTelemetryEntity();
        entity.setDeviceId(dto.getDeviceId());
        entity.setTimestamp(dto.getTimestamp());
        entity.setCpuUsage(dto.getCpuUsage());
        entity.setMemoryUsagePercentage(dto.getMemoryUsagePercentage());
        entity.setStorageUsagePercentage(dto.getStorageUsagePercentage());
        entity.setSystemTemperature(dto.getSystemTemperature());
        entity.setBatteryPercentage(dto.getBatteryPercentage());

        telemetryRepository.save(entity);
        diagnosticEngine.analyzeTelemetry(entity);

        return ResponseEntity.ok().build();
    }
}
