import React from 'react';
import './Settings.css';

export default function Settings() {
  return (
    <div className="max-w-4xl">
      <h2 className="text-2xl font-bold mb-6">Settings & Preferences</h2>
      
      <div className="grid grid-cols-2 gap-6">
        <div className="flex-col gap-6">
          <div className="card">
            <h3 className="text-lg font-semibold mb-4 border-bottom pb-2">Monitoring Permissions</h3>
            <div className="flex-col gap-4">
              <ToggleRow label="Hardware Monitoring" description="Track CPU, Memory, Disk, and Temp." defaultChecked />
              <ToggleRow label="Software Monitoring" description="Monitor installed applications." defaultChecked />
              <ToggleRow label="Process Monitoring" description="Track active processes and resource usage." defaultChecked />
              <ToggleRow label="System Event Monitoring" description="Monitor system logs and events." defaultChecked />
              <ToggleRow label="Application Monitoring" description="Detailed application level tracing." defaultChecked />
              <ToggleRow label="Automation Permission" description="Allow system to auto-fix issues." defaultChecked={false} />
            </div>
          </div>
        </div>

        <div className="flex-col gap-6">
          <div className="card">
            <h3 className="text-lg font-semibold mb-4 border-bottom pb-2">Privacy</h3>
            <div className="flex-col gap-4">
              <ToggleRow label="Local Monitoring Only" description="Data never leaves your machine." defaultChecked />
              <ToggleRow label="Data Collection" description="Share anonymous telemetry for product improvement." defaultChecked={false} />
              
              <div className="mt-2">
                <label className="text-sm font-medium mb-1 block">Data Retention</label>
                <select className="w-full p-2 border rounded-md bg-surface-main">
                  <option>7 Days</option>
                  <option>30 Days</option>
                  <option>90 Days</option>
                </select>
              </div>
            </div>
          </div>

          <div className="card">
            <h3 className="text-lg font-semibold mb-4 border-bottom pb-2">Performance Limits</h3>
            <div className="text-sm text-secondary mb-4">Monitoring Resource Limit</div>
            
            <div className="flex-col gap-4">
              <div>
                <div className="flex justify-between text-sm mb-1">
                  <label className="font-medium">CPU Usage Target</label>
                  <span className="text-muted">Max 5%</span>
                </div>
                <input type="range" className="w-full" min="1" max="10" defaultValue="5" />
              </div>
              
              <div>
                <div className="flex justify-between text-sm mb-1">
                  <label className="font-medium">Memory Usage Target</label>
                  <span className="text-muted">Max 250MB</span>
                </div>
                <input type="range" className="w-full" min="50" max="500" defaultValue="250" />
              </div>

              <div className="mt-2">
                <label className="text-sm font-medium mb-1 block">Monitoring Frequency</label>
                <select className="w-full p-2 border rounded-md bg-surface-main">
                  <option>Real-time (1s)</option>
                  <option>Balanced (5s)</option>
                  <option>Battery Saver (30s)</option>
                </select>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <div className="mt-8 flex justify-end gap-3">
        <button className="btn btn-outline">Cancel</button>
        <button className="btn btn-primary">Save Changes</button>
      </div>
    </div>
  );
}

function ToggleRow({ label, description, defaultChecked }) {
  return (
    <div className="flex items-center justify-between">
      <div>
        <div className="text-sm font-medium">{label}</div>
        <div className="text-xs text-muted">{description}</div>
      </div>
      <label className="toggle-switch">
        <input type="checkbox" defaultChecked={defaultChecked} />
        <span className="slider"></span>
      </label>
    </div>
  );
}
