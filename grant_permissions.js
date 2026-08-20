const payload = {
    deviceId: "e8cc7f9d-f295-4dce-94cf-7786ef555620",
    hardwareMonitoring: true,
    processMonitoring: true,
    softwareMonitoring: true,
    systemEventMonitoring: true,
    diagnosticMonitoring: true,
    automationPermission: true
};

fetch("http://localhost:8080/api/devices/e8cc7f9d-f295-4dce-94cf-7786ef555620/permissions", {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
})
.then(r => r.json())
.then(data => console.log("Permissions updated:", data))
.catch(console.error);
