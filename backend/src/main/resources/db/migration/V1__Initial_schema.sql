CREATE TABLE devices (
    id BIGSERIAL PRIMARY KEY,
    device_id VARCHAR(255) UNIQUE NOT NULL,
    device_name VARCHAR(255),
    operating_system VARCHAR(255),
    os_version VARCHAR(255),
    processor VARCHAR(255),
    total_memory BIGINT,
    agent_version VARCHAR(50),
    status VARCHAR(50),
    last_seen TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE device_permissions (
    device_id VARCHAR(255) PRIMARY KEY REFERENCES devices(device_id) ON DELETE CASCADE,
    hardware_monitoring BOOLEAN DEFAULT true,
    process_monitoring BOOLEAN DEFAULT true,
    software_monitoring BOOLEAN DEFAULT true,
    system_event_monitoring BOOLEAN DEFAULT true,
    diagnostic_monitoring BOOLEAN DEFAULT true,
    automation_permission BOOLEAN DEFAULT false,
    updated_at TIMESTAMP
);

CREATE TABLE system_telemetry (
    id BIGSERIAL PRIMARY KEY,
    device_id VARCHAR(255) REFERENCES devices(device_id) ON DELETE CASCADE,
    timestamp TIMESTAMP NOT NULL,
    cpu_usage DOUBLE PRECISION,
    memory_usage_percentage DOUBLE PRECISION,
    storage_usage_percentage DOUBLE PRECISION,
    system_temperature DOUBLE PRECISION,
    battery_percentage DOUBLE PRECISION
);
CREATE INDEX idx_telemetry_device_time ON system_telemetry(device_id, timestamp);

CREATE TABLE incidents (
    id BIGSERIAL PRIMARY KEY,
    incident_id VARCHAR(255) UNIQUE NOT NULL,
    device_id VARCHAR(255) REFERENCES devices(device_id) ON DELETE CASCADE,
    type VARCHAR(100),
    severity VARCHAR(50),
    status VARCHAR(50),
    detected_at TIMESTAMP NOT NULL,
    resolved_at TIMESTAMP,
    summary TEXT
);

CREATE TABLE action_recommendations (
    id BIGSERIAL PRIMARY KEY,
    action_id VARCHAR(255) UNIQUE NOT NULL,
    incident_id VARCHAR(255) REFERENCES incidents(incident_id) ON DELETE CASCADE,
    action_type VARCHAR(100),
    description TEXT,
    risk_level VARCHAR(50),
    requires_user_approval BOOLEAN DEFAULT true,
    status VARCHAR(50)
);
