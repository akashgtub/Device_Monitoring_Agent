package com.devicemonitoring.backend.device;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceRepository deviceRepository;
    private final DevicePermissionRepository permissionRepository;

    public DeviceController(DeviceRepository deviceRepository, DevicePermissionRepository permissionRepository) {
        this.deviceRepository = deviceRepository;
        this.permissionRepository = permissionRepository;
    }

    @Data
    public static class RegistrationRequest {
        private String deviceId;
        private String deviceName;
        private String operatingSystem;
        private String osVersion;
        private String agentVersion;
    }

    @Data
    public static class RegistrationResponse {
        private String token;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerDevice(@RequestBody RegistrationRequest request) {
        Device device = deviceRepository.findByDeviceId(request.getDeviceId())
                .orElse(new Device());
        
        device.setDeviceId(request.getDeviceId());
        device.setDeviceName(request.getDeviceName());
        device.setOperatingSystem(request.getOperatingSystem());
        device.setOsVersion(request.getOsVersion());
        device.setAgentVersion(request.getAgentVersion());
        device.setStatus("ONLINE");
        
        if (device.getToken() == null) {
            device.setToken("dev-token-" + UUID.randomUUID().toString());
        }
        
        deviceRepository.save(device);

        // Ensure permissions record exists
        if (!permissionRepository.existsById(request.getDeviceId())) {
            DevicePermission permission = new DevicePermission();
            permission.setDeviceId(request.getDeviceId());
            permissionRepository.save(permission);
        }

        RegistrationResponse response = new RegistrationResponse();
        response.setToken(device.getToken());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{deviceId}/permissions")
    public ResponseEntity<DevicePermission> getPermissions(@PathVariable String deviceId) {
        return permissionRepository.findById(deviceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{deviceId}/permissions")
    public ResponseEntity<DevicePermission> updatePermissions(@PathVariable String deviceId, @RequestBody DevicePermission updatedPermissions) {
        Optional<DevicePermission> existingOpt = permissionRepository.findById(deviceId);
        if (existingOpt.isPresent()) {
            DevicePermission existing = existingOpt.get();
            existing.setHardwareMonitoring(updatedPermissions.isHardwareMonitoring());
            existing.setProcessMonitoring(updatedPermissions.isProcessMonitoring());
            existing.setSoftwareMonitoring(updatedPermissions.isSoftwareMonitoring());
            existing.setSystemEventMonitoring(updatedPermissions.isSystemEventMonitoring());
            existing.setDiagnosticMonitoring(updatedPermissions.isDiagnosticMonitoring());
            existing.setAutomationPermission(updatedPermissions.isAutomationPermission());
            
            DevicePermission saved = permissionRepository.save(existing);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }
}
