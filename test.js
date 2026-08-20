const payload = {
    deviceId: "e8cc7f9d-f295-4dce-94cf-7786ef555620",
    incidentId: 1,
    actionType: "RUN_SYSTEM_DIAGNOSTIC",
    description: "Test automation action",
    riskLevel: "LOW",
    requiresConfirmation: true
};

fetch("http://localhost:8080/api/actions/request", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
})
.then(r => r.json())
.then(data => {
    console.log("Requested:", data);
    return fetch("http://localhost:8080/api/actions/" + data.id + "/approve", { method: "POST" });
})
.then(r => r.text())
.then(data => console.log("Approval response:", data))
.catch(console.error);
