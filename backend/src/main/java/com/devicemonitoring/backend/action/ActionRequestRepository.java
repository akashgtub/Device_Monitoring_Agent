package com.devicemonitoring.backend.action;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRequestRepository extends JpaRepository<ActionRequest, String> {
    List<ActionRequest> findByDeviceId(String deviceId);
    List<ActionRequest> findByDeviceIdAndStatus(String deviceId, String status);
}
