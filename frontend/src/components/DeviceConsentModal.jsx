import React, { useState } from 'react';
import './DeviceConsentModal.css';

const DeviceConsentModal = ({ deviceId, onSave }) => {
    const [permissions, setPermissions] = useState({
        hardwareMonitoring: true,
        processMonitoring: true,
        softwareMonitoring: true,
        systemEventMonitoring: true,
        diagnosticMonitoring: true,
        automationPermission: false
    });

    const handleChange = (e) => {
        const { name, checked } = e.target;
        setPermissions(prev => ({
            ...prev,
            [name]: checked
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`/api/devices/${deviceId}/permissions`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(permissions),
            });
            if (response.ok) {
                const data = await response.json();
                onSave(data);
            } else {
                console.error("Failed to save permissions");
            }
        } catch (error) {
            console.error("Error saving permissions:", error);
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content glass-panel">
                <h2>Device Permissions Required</h2>
                <p>Please configure what the agent is allowed to do on this device ({deviceId}).</p>
                
                <form onSubmit={handleSubmit} className="permissions-form">
                    <div className="permission-group">
                        <h3>Monitoring Permissions</h3>
                        <label className="toggle-label">
                            <input type="checkbox" name="hardwareMonitoring" checked={permissions.hardwareMonitoring} onChange={handleChange} />
                            Hardware Telemetry (CPU, RAM)
                        </label>
                        <label className="toggle-label">
                            <input type="checkbox" name="processMonitoring" checked={permissions.processMonitoring} onChange={handleChange} />
                            Process Monitoring
                        </label>
                        <label className="toggle-label">
                            <input type="checkbox" name="softwareMonitoring" checked={permissions.softwareMonitoring} onChange={handleChange} />
                            Software Inventory
                        </label>
                    </div>

                    <div className="permission-group automation-group">
                        <h3>Automation & Auto-Fix</h3>
                        <p className="warning-text">By enabling this, you allow the system to recommend and execute safe, allowlisted actions to resolve issues. You will still be prompted for confirmation for most actions.</p>
                        <label className="toggle-label danger-toggle">
                            <input type="checkbox" name="automationPermission" checked={permissions.automationPermission} onChange={handleChange} />
                            Enable Safe Automation
                        </label>
                    </div>

                    <div className="modal-actions">
                        <button type="submit" className="btn-primary">Save & Continue</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default DeviceConsentModal;
