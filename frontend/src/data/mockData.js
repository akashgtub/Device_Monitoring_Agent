export const kpis = [
  { id: 'cpu', label: 'CPU Usage', value: '32%', trend: 'down', trendValue: '2%', icon: 'Cpu' },
  { id: 'mem', label: 'Memory Usage', value: '64%', trend: 'up', trendValue: '12%', icon: 'MemoryStick' },
  { id: 'disk', label: 'Disk Usage', value: '58%', trend: 'stable', trendValue: '0%', icon: 'HardDrive' },
  { id: 'temp', label: 'Temperature', value: '58°C', trend: 'down', trendValue: '3°C', icon: 'Thermometer' },
  { id: 'net', label: 'Network', value: '42 Mbps', trend: 'up', trendValue: '5 Mbps', icon: 'Network' },
  { id: 'bat', label: 'Battery', value: '82%', trend: 'down', trendValue: 'Charging', icon: 'Battery' }
];

export const hardwareHealth = [
  { name: 'CPU', usage: '32%', detail1: '3.4 GHz', detail2: '58°C', status: 'Healthy' },
  { name: 'Memory', usage: '64%', detail1: '10.2 GB Used', detail2: '16.0 GB Total', status: 'Healthy' },
  { name: 'Storage', usage: '58%', detail1: '290 GB Used', detail2: '500 GB Total', status: 'Healthy' },
  { name: 'GPU', usage: '14%', detail1: '2.1 GB VRAM', detail2: '45°C', status: 'Healthy' },
];

export const runningApps = [
  { id: 1, name: 'Chrome', cpu: '32%', memory: '4.2 GB', status: 'Running' },
  { id: 2, name: 'Visual Studio Code', cpu: '12%', memory: '1.8 GB', status: 'Running' },
  { id: 3, name: 'Docker Desktop', cpu: '18%', memory: '2.4 GB', status: 'Running' },
  { id: 4, name: 'Slack', cpu: '2%', memory: '0.8 GB', status: 'Running' },
];

export const recentIssues = [
  { id: 1, title: 'High Memory Usage', severity: 'High', time: '10 mins ago' },
  { id: 2, title: 'Disk Space Low', severity: 'Medium', time: '1 hour ago' },
  { id: 3, title: 'Application Not Responding', severity: 'Medium', time: '2 hours ago' },
];

export const systemEvents = [
  { id: 1, app: 'Chrome', event: 'High Memory Consumption', severity: 'Warning', time: '10:42 AM' },
  { id: 2, app: 'Windows Update', event: 'Service Started', severity: 'Info', time: '09:15 AM' },
  { id: 3, app: 'Docker Desktop', event: 'Container crashed', severity: 'Error', time: '08:30 AM' },
];
