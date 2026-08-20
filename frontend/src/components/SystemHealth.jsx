import React from 'react';
import './SystemHealth.css';

export default function SystemHealth({ status = 'Healthy', score = 98, detail = 'All systems operating normally' }) {
  const isHealthy = status === 'Healthy';
  const colorVar = isHealthy ? 'var(--color-success)' : 'var(--color-warning)';
  
  return (
    <div className="card flex items-center justify-between">
      <div className="flex-col gap-2">
        <h2 className="text-xl font-semibold">System Status</h2>
        <div className="flex items-center gap-3">
          <div className={`badge ${isHealthy ? 'badge-success' : 'badge-warning'}`}>
            {status}
          </div>
          <span className="text-sm text-secondary">{detail}</span>
        </div>
      </div>
      
      <div className="health-ring-container">
        <svg viewBox="0 0 100 100" className="health-ring">
          <circle 
            cx="50" cy="50" r="40" 
            className="ring-bg"
          />
          <circle 
            cx="50" cy="50" r="40" 
            className="ring-progress"
            style={{ 
              stroke: colorVar,
              strokeDasharray: `${score * 2.51} 251.2` 
            }}
          />
        </svg>
        <div className="health-score">
          <span className="score-value">{score}</span>
          <span className="score-percent">%</span>
        </div>
      </div>
    </div>
  );
}
