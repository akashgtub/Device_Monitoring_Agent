import React from 'react';
import { NavLink } from 'react-router-dom';
import { 
  LayoutDashboard, 
  Activity, 
  Cpu, 
  Box, 
  AlertCircle, 
  Stethoscope, 
  Lightbulb, 
  Zap, 
  FileText, 
  Settings, 
  HelpCircle,
  ShieldCheck
} from 'lucide-react';
import './Sidebar.css';

const navItems = [
  { path: '/', label: 'Dashboard', icon: LayoutDashboard },
  { path: '/monitor', label: 'System Monitor', icon: Activity },
  { path: '/hardware', label: 'Hardware', icon: Cpu },
  { path: '/software', label: 'Software', icon: Box },
  { path: '/issues', label: 'Issues', icon: AlertCircle },
  { path: '/diagnostics', label: 'Diagnostics', icon: Stethoscope },
  { path: '/solutions', label: 'Solutions', icon: Lightbulb },
  { path: '/automation', label: 'Automation', icon: Zap },
  { path: '/reports', label: 'Reports', icon: FileText },
  { path: '/settings', label: 'Settings', icon: Settings },
  { path: '/help', label: 'Help & Support', icon: HelpCircle },
];

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar-header">
        <div className="logo-container">
          <div className="logo-icon"><Activity size={24} color="var(--color-primary)" /></div>
          <div>
            <h1 className="logo-title">Device Monitoring</h1>
            <p className="logo-subtitle">Intelligent System Health</p>
          </div>
        </div>
      </div>
      
      <nav className="sidebar-nav main-scroll-area">
        {navItems.map((item) => {
          const Icon = item.icon;
          return (
            <NavLink 
              key={item.path} 
              to={item.path} 
              className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}
            >
              <Icon size={20} className="nav-icon" />
              <span>{item.label}</span>
            </NavLink>
          );
        })}
      </nav>

      <div className="sidebar-footer">
        <div className="protection-card">
          <div className="protection-header flex items-center gap-2">
            <ShieldCheck size={20} className="text-success" color="var(--color-success)" />
            <span className="font-semibold text-sm">System Protection</span>
          </div>
          <div className="protection-status text-xs text-muted mt-1">
            Active
          </div>
        </div>
        <div className="user-profile flex items-center gap-3 mt-4">
          <div className="avatar">A</div>
          <div className="user-info">
            <div className="text-sm font-semibold">Admin User</div>
            <div className="text-xs text-muted">Local System</div>
          </div>
        </div>
      </div>
    </aside>
  );
}
