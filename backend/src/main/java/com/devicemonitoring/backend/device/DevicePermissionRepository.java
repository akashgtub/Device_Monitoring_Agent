package com.devicemonitoring.backend.device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevicePermissionRepository extends JpaRepository<DevicePermission, String> {
}
