package com.devicemonitoring.backend.telemetry;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TelemetryRepository extends JpaRepository<SystemTelemetryEntity, Long> {
    List<SystemTelemetryEntity> findTop10ByDeviceIdOrderByTimestampDesc(String deviceId);
}
