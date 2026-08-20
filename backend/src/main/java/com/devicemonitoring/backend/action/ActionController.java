package com.devicemonitoring.backend.action;

import com.devicemonitoring.backend.device.DevicePermission;
import com.devicemonitoring.backend.device.DevicePermissionRepository;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/actions")
public class ActionController {

    private final ActionRequestRepository actionRepository;
    private final DevicePermissionRepository permissionRepository;

    // Fixed allowlist of safe actions
    private static final Set<String> ALLOWED_ACTIONS = Set.of(
            "REFRESH_MONITORING_DATA",
            "RUN_SYSTEM_DIAGNOSTIC",
            "RESTART_AGENT",
            "RESTART_APPLICATION",
            "RESTART_NON_CRITICAL_SERVICE"
    );

    public ActionController(ActionRequestRepository actionRepository, DevicePermissionRepository permissionRepository) {
        this.actionRepository = actionRepository;
        this.permissionRepository = permissionRepository;
    }

    @PostMapping("/request")
    public ResponseEntity<ActionRequest> requestAction(@RequestBody ActionRequest request) {
        if (!ALLOWED_ACTIONS.contains(request.getActionType())) {
            return ResponseEntity.badRequest().build();
        }
        
        request.setStatus("REQUESTED");
        ActionRequest saved = actionRepository.save(request);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{actionId}/approve")
    public ResponseEntity<ActionRequest> approveAction(@PathVariable String actionId) {
        Optional<ActionRequest> actionOpt = actionRepository.findById(actionId);
        if (actionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ActionRequest action = actionOpt.get();
        
        // Backend Policy Check
        Optional<DevicePermission> permissionOpt = permissionRepository.findById(action.getDeviceId());
        if (permissionOpt.isEmpty() || !permissionOpt.get().isAutomationPermission()) {
            action.setStatus("FAILED");
            action.setResult("Automation permission not granted by user.");
            actionRepository.save(action);
            return ResponseEntity.badRequest().body(action);
        }

        if (!ALLOWED_ACTIONS.contains(action.getActionType())) {
            action.setStatus("FAILED");
            action.setResult("Action not in allowlist.");
            actionRepository.save(action);
            return ResponseEntity.badRequest().body(action);
        }

        action.setStatus("APPROVED");
        ActionRequest saved = actionRepository.save(action);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{actionId}/reject")
    public ResponseEntity<ActionRequest> rejectAction(@PathVariable String actionId) {
        Optional<ActionRequest> actionOpt = actionRepository.findById(actionId);
        if (actionOpt.isPresent()) {
            ActionRequest action = actionOpt.get();
            action.setStatus("REJECTED");
            ActionRequest saved = actionRepository.save(action);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{actionId}")
    public ResponseEntity<ActionRequest> getAction(@PathVariable String actionId) {
        return actionRepository.findById(actionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint for Agent polling
    @GetMapping("/device/{deviceId}/pending")
    public ResponseEntity<List<ActionRequest>> getPendingActions(@PathVariable String deviceId) {
        List<ActionRequest> pending = actionRepository.findByDeviceIdAndStatus(deviceId, "APPROVED");
        return ResponseEntity.ok(pending);
    }

    @Data
    public static class ActionResultDto {
        private String status;
        private String result;
    }

    // Endpoint for Agent reporting result
    @PostMapping("/{actionId}/result")
    public ResponseEntity<ActionRequest> reportResult(@PathVariable String actionId, @RequestBody ActionResultDto resultDto) {
        Optional<ActionRequest> actionOpt = actionRepository.findById(actionId);
        if (actionOpt.isPresent()) {
            ActionRequest action = actionOpt.get();
            action.setStatus(resultDto.getStatus()); // SUCCESS or FAILED
            action.setResult(resultDto.getResult());
            action.setExecutedAt(Instant.now());
            ActionRequest saved = actionRepository.save(action);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }
}
