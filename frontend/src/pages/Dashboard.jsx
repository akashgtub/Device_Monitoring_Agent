import React from 'react';
import SystemHealth from '../components/SystemHealth';
import MetricCard from '../components/MetricCard';
import { kpis, hardwareHealth, runningApps } from '../data/mockData';
import { ChevronRight, Cpu, HardDrive, Zap, Info, ShieldAlert } from 'lucide-react';
import DeviceConsentModal from '../components/DeviceConsentModal';
import ActionApprovalModal from '../components/ActionApprovalModal';
import { useState, useEffect } from 'react';
import './Dashboard.css';

export default function Dashboard() {
  const [showConsent, setShowConsent] = useState(false);
  const [showApproval, setShowApproval] = useState(false);
  const [recommendation, setRecommendation] = useState(null);
  const [deviceId, setDeviceId] = useState("device-mock-123"); // Mock for now

  // In a real app, we'd check if permissions exist on mount
  useEffect(() => {
    // Check if we need to show consent on load
    // setShowConsent(true);
  }, []);

  const triggerMockAutomation = async () => {
    // 1. Backend creates action request
    try {
      const res = await fetch('/api/actions/request', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          deviceId: deviceId,
          incidentId: 1,
          actionType: 'RUN_SYSTEM_DIAGNOSTIC',
          description: 'Run system diagnostic to investigate issues',
          riskLevel: 'LOW',
          requiresConfirmation: true
        })
      });
      
      if (res.ok) {
        const actionReq = await res.json();
        setRecommendation({
          actionId: actionReq.id,
          problem: 'System performance degradation detected',
          actionDescription: actionReq.description,
          riskLevel: actionReq.riskLevel,
          expectedResult: 'Gather safe diagnostic data to identify root cause'
        });
        setShowApproval(true);
      } else {
        alert("Automation test failed. Ensure backend is running and action is in allowlist.");
      }
    } catch (e) {
      console.error(e);
      alert("Failed to connect to backend for automation test.");
    }
  };

  const handleApproveAction = async (actionId) => {
    try {
      const res = await fetch(`/api/actions/${actionId}/approve`, { method: 'POST' });
      if (res.ok) {
        alert("Action approved! The Agent will now poll, execute it, and return the result.");
        setShowApproval(false);
      } else {
        const error = await res.json();
        alert("Failed to approve: " + error.result);
      }
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <div className="dashboard-container">
      {showConsent && (
        <DeviceConsentModal 
          deviceId={deviceId} 
          onSave={() => setShowConsent(false)} 
        />
      )}
      
      {showApproval && recommendation && (
        <ActionApprovalModal 
          recommendation={recommendation}
          onApprove={handleApproveAction}
          onCancel={() => setShowApproval(false)}
        />
      )}

      <div className="dashboard-main">
        <section className="mb-8">
          <SystemHealth status="Healthy" score={98} detail="System is operating under normal parameters." />
        </section>

        <section className="mb-8">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold">Key Performance Indicators</h3>
          </div>
          <div className="kpi-grid">
            {kpis.map(kpi => (
              <MetricCard key={kpi.id} {...kpi} />
            ))}
          </div>
        </section>

        <section className="mb-8">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold">Hardware Health</h3>
            <button className="btn btn-outline text-sm">View Details</button>
          </div>
          <div className="hardware-grid">
            {hardwareHealth.map(hw => (
              <div key={hw.name} className="card hardware-card">
                <div className="flex items-center justify-between mb-3">
                  <div className="font-semibold">{hw.name}</div>
                  <div className="badge badge-success">{hw.status}</div>
                </div>
                <div className="flex items-baseline gap-2 mb-4">
                  <span className="text-2xl font-bold">{hw.usage}</span>
                  <span className="text-sm text-muted">Utilization</span>
                </div>
                <div className="hardware-details text-sm text-secondary">
                  <div className="flex justify-between py-1 border-bottom">
                    <span>Detail:</span>
                    <span className="font-medium text-main">{hw.detail1}</span>
                  </div>
                  <div className="flex justify-between py-1">
                    <span>Metrics:</span>
                    <span className="font-medium text-main">{hw.detail2}</span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </section>

        <section>
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold">Software Health</h3>
            <div className="flex gap-2">
                <button onClick={() => setShowConsent(true)} className="btn btn-outline text-sm text-warning border-warning">
                    <ShieldAlert size={16} /> Manage Permissions
                </button>
                <button className="btn btn-outline text-sm">All Processes</button>
            </div>
          </div>
          <div className="card p-0 overflow-hidden">
            <table className="software-table w-full">
              <thead>
                <tr>
                  <th>Application</th>
                  <th>CPU</th>
                  <th>Memory</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {runningApps.map(app => (
                  <tr key={app.id}>
                    <td className="font-medium">{app.name}</td>
                    <td>{app.cpu}</td>
                    <td>{app.memory}</td>
                    <td>
                      <span className="badge badge-success">{app.status}</span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </section>
      </div>

      <aside className="dashboard-sidebar">
        <div className="card mb-4 flex-col items-center p-6 text-center">
          <h4 className="font-semibold mb-4 w-full text-left">System Health</h4>
          <div className="relative w-32 h-32 flex items-center justify-center mb-4">
            <svg viewBox="0 0 100 100" className="w-full h-full transform -rotate-90">
              <circle cx="50" cy="50" r="40" fill="none" stroke="var(--color-surface-secondary)" strokeWidth="8" />
              <circle cx="50" cy="50" r="40" fill="none" stroke="var(--color-warning)" strokeWidth="8" strokeDasharray="170 251.2" />
            </svg>
            <div className="absolute text-2xl font-bold">68%</div>
          </div>
          <div className="badge badge-warning mb-2">Needs Attention</div>
          <p className="text-sm text-muted">Memory pressure detected</p>
        </div>

        <div className="card mb-4">
          <h4 className="font-semibold mb-4">Quick Stats</h4>
          <div className="flex-col gap-4">
            <div className="flex justify-between items-center text-sm">
              <span className="text-secondary">CPU Usage</span>
              <span className="font-semibold">32%</span>
            </div>
            <div className="flex justify-between items-center text-sm">
              <span className="text-secondary">Memory Usage</span>
              <span className="font-semibold text-critical">94%</span>
            </div>
            <div className="flex justify-between items-center text-sm">
              <span className="text-secondary">Disk Usage</span>
              <span className="font-semibold">85%</span>
            </div>
            <div className="flex justify-between items-center text-sm">
              <span className="text-secondary">Temperature</span>
              <span className="font-semibold">58°C</span>
            </div>
          </div>
        </div>

        <div className="card">
          <h4 className="font-semibold mb-4">Recent Issues</h4>
          <div className="flex-col gap-3 mb-4">
            <div className="issue-mini-card">
              <div className="flex items-start gap-2">
                <Info size={16} className="text-critical mt-1" />
                <div>
                  <div className="text-sm font-semibold">High Memory Usage</div>
                  <div className="text-xs text-muted">High Severity</div>
                </div>
              </div>
            </div>
            <div className="issue-mini-card">
              <div className="flex items-start gap-2">
                <Info size={16} className="text-warning mt-1" />
                <div>
                  <div className="text-sm font-semibold">Disk Space Low</div>
                  <div className="text-xs text-muted">Medium Severity</div>
                </div>
              </div>
            </div>
          </div>
          <button className="btn btn-outline w-full justify-center mb-2">View All Issues</button>
          <button onClick={triggerMockAutomation} className="btn btn-primary w-full justify-center" style={{background: 'var(--primary-color)'}}>
            Test Automation
          </button>
        </div>
      </aside>
    </div>
  );
}
