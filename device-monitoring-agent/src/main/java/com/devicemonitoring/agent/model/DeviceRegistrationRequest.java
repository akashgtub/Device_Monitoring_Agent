package com.devicemonitoring.agent.model;

import lombok.Data;

@Data
public class DeviceRegistrationRequest {
    private String deviceId;
    private String deviceName;
    private String operatingSystem;
    private String osVersion;
    private String agentVersion;
}
