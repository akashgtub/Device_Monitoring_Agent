import React from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, AreaChart, Area } from 'recharts';
import { runningApps } from '../data/mockData';
import './SystemMonitor.css';

// Mock data for graphs
const timeData = Array.from({ length: 20 }, (_, i) => ({
  time: `10:${(i * 3).toString().padStart(2, '0')}`,
  cpu: Math.floor(Math.random() * 40) + 20,
  memory: Math.floor(Math.random() * 20) + 60,
  disk: Math.floor(Math.random() * 80) + 10,
  network: Math.floor(Math.random() * 100),
}));

export default function SystemMonitor() {
  return (
    <div className="monitor-container">
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-2xl font-bold">Real-time System Monitor</h2>
        <div className="badge badge-success">Live Updates Active</div>
      </div>

      <div className="graphs-grid mb-8">
        <div className="card graph-card">
          <h4 className="font-semibold mb-4 text-sm text-secondary">CPU Utilization</h4>
          <div className="graph-wrapper">
            <ResponsiveContainer width="100%" height="100%">
              <AreaChart data={timeData} margin={{ top: 5, right: 0, left: -20, bottom: 0 }}>
                <defs>
                  <linearGradient id="colorCpu" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="5%" stopColor="var(--color-primary)" stopOpacity={0.3}/>
                    <stop offset="95%" stopColor="var(--color-primary)" stopOpacity={0}/>
                  </linearGradient>
                </defs>
                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="var(--color-border)" />
                <XAxis dataKey="time" tick={{fontSize: 10, fill: 'var(--color-text-muted)'}} />
                <YAxis tick={{fontSize: 10, fill: 'var(--color-text-muted)'}} />
                <Tooltip />
                <Area type="monotone" dataKey="cpu" stroke="var(--color-primary)" fillOpacity={1} fill="url(#colorCpu)" />
              </AreaChart>
            </ResponsiveContainer>
          </div>
        </div>

        <div className="card graph-card">
          <h4 className="font-semibold mb-4 text-sm text-secondary">Memory Usage</h4>
          <div className="graph-wrapper">
            <ResponsiveContainer width="100%" height="100%">
              <AreaChart data={timeData} margin={{ top: 5, right: 0, left: -20, bottom: 0 }}>
                <defs>
                  <linearGradient id="colorMem" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="5%" stopColor="var(--color-warning)" stopOpacity={0.3}/>
                    <stop offset="95%" stopColor="var(--color-warning)" stopOpacity={0}/>
                  </linearGradient>
                </defs>
                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="var(--color-border)" />
                <XAxis dataKey="time" tick={{fontSize: 10, fill: 'var(--color-text-muted)'}} />
                <YAxis tick={{fontSize: 10, fill: 'var(--color-text-muted)'}} />
                <Tooltip />
                <Area type="monotone" dataKey="memory" stroke="var(--color-warning)" fillOpacity={1} fill="url(#colorMem)" />
              </AreaChart>
            </ResponsiveContainer>
          </div>
        </div>

        <div className="card graph-card">
          <h4 className="font-semibold mb-4 text-sm text-secondary">Disk Activity</h4>
          <div className="graph-wrapper">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={timeData} margin={{ top: 5, right: 0, left: -20, bottom: 0 }}>
                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="var(--color-border)" />
                <XAxis dataKey="time" tick={{fontSize: 10, fill: 'var(--color-text-muted)'}} />
                <YAxis tick={{fontSize: 10, fill: 'var(--color-text-muted)'}} />
                <Tooltip />
                <Line type="monotone" dataKey="disk" stroke="var(--color-success)" strokeWidth={2} dot={false} />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>

        <div className="card graph-card">
          <h4 className="font-semibold mb-4 text-sm text-secondary">Network Traffic</h4>
          <div className="graph-wrapper">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={timeData} margin={{ top: 5, right: 0, left: -20, bottom: 0 }}>
                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="var(--color-border)" />
                <XAxis dataKey="time" tick={{fontSize: 10, fill: 'var(--color-text-muted)'}} />
                <YAxis tick={{fontSize: 10, fill: 'var(--color-text-muted)'}} />
                <Tooltip />
                <Line type="monotone" dataKey="network" stroke="#8b5cf6" strokeWidth={2} dot={false} />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>

      <h3 className="text-lg font-semibold mb-4">Process Activity</h3>
      <div className="card p-0 overflow-hidden">
        <table className="monitor-table w-full">
          <thead>
            <tr>
              <th>Process Name</th>
              <th>PID</th>
              <th>CPU %</th>
              <th>Memory (MB)</th>
              <th>Disk (MB/s)</th>
              <th>Network (Kbps)</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {runningApps.map((app, i) => (
              <tr key={app.id}>
                <td className="font-medium">{app.name}</td>
                <td className="text-muted">{4124 + i * 112}</td>
                <td>{app.cpu.replace('%', '')}</td>
                <td>{parseFloat(app.memory) * 1024}</td>
                <td>{(Math.random() * 5).toFixed(1)}</td>
                <td>{Math.floor(Math.random() * 500)}</td>
                <td><span className="badge badge-success">Running</span></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
