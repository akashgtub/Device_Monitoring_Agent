import React from 'react';
import { AlertTriangle, User, Zap, Activity } from 'lucide-react';
import './Issues.css';

export default function Issues() {
  return (
    <div className="issues-container max-w-4xl mx-auto">
      <div className="mb-8">
        <div className="flex items-center gap-3 mb-2">
          <div className="w-10 h-10 rounded-full bg-critical-light flex items-center justify-center text-critical">
            <AlertTriangle size={24} />
          </div>
          <h2 className="text-2xl font-bold">SYSTEM ISSUE DETECTED</h2>
        </div>
        <p className="text-secondary ml-13">A critical event has occurred that requires your attention.</p>
      </div>

      <div className="card mb-8 border-critical">
        <div className="flex items-start justify-between mb-4">
          <div>
            <h3 className="text-xl font-bold text-main mb-1">High Memory Usage</h3>
            <p className="text-secondary text-sm">Detected 10 minutes ago on SYSTEM_ROOT</p>
          </div>
          <div className="flex gap-2">
            <div className="badge badge-critical">Severity: High</div>
            <div className="badge badge-warning">Confidence: 89%</div>
          </div>
        </div>
        
        <div className="p-4 bg-surface-secondary rounded-md border text-sm text-secondary mb-4">
          <span className="font-semibold text-main">Description: </span>
          Google Chrome is consuming excessive memory, causing memory pressure and increased disk paging, which may lead to system slowdown.
        </div>
      </div>

      <h3 className="text-lg font-semibold mb-4">Root Cause Analysis</h3>
      <div className="card mb-8">
        <div className="flex flex-col gap-6">
          
          <div>
            <div className="text-sm text-secondary mb-1">Probable Cause:</div>
            <div className="text-lg font-semibold text-main">High Memory Usage by Google Chrome</div>
          </div>
          
          <div>
            <div className="text-sm text-secondary mb-2">Key Evidence:</div>
            <div className="grid grid-cols-2 gap-4">
              <div className="flex items-center gap-2 p-3 bg-surface-secondary rounded-md border">
                <Activity size={16} className="text-critical" />
                <span className="text-sm">RAM Usage: <span className="font-semibold text-critical">94%</span></span>
              </div>
              <div className="flex items-center gap-2 p-3 bg-surface-secondary rounded-md border">
                <Activity size={16} className="text-critical" />
                <span className="text-sm">Chrome Memory Usage: <span className="font-semibold text-critical">6.8 GB</span></span>
              </div>
              <div className="flex items-center gap-2 p-3 bg-surface-secondary rounded-md border">
                <Activity size={16} className="text-warning" />
                <span className="text-sm">Disk Activity: <span className="font-semibold text-warning">High</span></span>
              </div>
              <div className="flex items-center gap-2 p-3 bg-surface-secondary rounded-md border">
                <Activity size={16} className="text-success" />
                <span className="text-sm">CPU Usage: <span className="font-semibold text-success">Normal</span></span>
              </div>
            </div>
          </div>
          
          <div className="flex gap-6">
            <div className="flex-1">
              <div className="text-sm text-secondary mb-1">Impact:</div>
              <div className="badge badge-critical">High</div>
            </div>
            <div className="flex-1">
              <div className="text-sm text-secondary mb-1">Confidence Score:</div>
              <div className="font-semibold text-main">89%</div>
            </div>
          </div>
          
          <div>
            <div className="text-sm text-secondary mb-1">Explanation:</div>
            <p className="text-sm text-main leading-relaxed">
              The system slowdown is primarily associated with memory pressure caused by Chrome. Increased memory consumption is resulting in higher disk paging activity.
            </p>
          </div>
          
        </div>
      </div>

      <div className="mb-4">
        <h3 className="text-lg font-semibold">Recommended Actions</h3>
        <p className="text-sm text-secondary">Choose how you want to resolve this issue</p>
      </div>

      <div className="action-cards-grid mb-6">
        <div className="card action-card action-card-manual">
          <div className="action-icon manual-icon">
            <User size={24} />
          </div>
          <h4 className="font-bold text-main mb-2">Option 1: Manual Fix</h4>
          <p className="text-sm text-secondary mb-6 flex-1">
            View step-by-step instructions and resolve the issue yourself.
          </p>
          <button className="btn btn-outline w-full justify-center text-primary border-primary">Go to Manual Guide</button>
        </div>
        
        <div className="card action-card action-card-auto">
          <div className="action-icon auto-icon">
            <Zap size={24} />
          </div>
          <h4 className="font-bold text-main mb-2">Option 2: AI Auto-Fix</h4>
          <p className="text-sm text-secondary mb-6 flex-1">
            Allow the system to perform the recommended corrective action automatically.
          </p>
          <button className="btn btn-primary w-full justify-center bg-success hover:bg-success-dark border-none">Start AI Auto-Fix</button>
        </div>
      </div>

      <div className="text-center text-xs text-muted">
        All actions are controlled by your permissions. You remain in control.
      </div>
    </div>
  );
}
