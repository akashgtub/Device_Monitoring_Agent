import React from 'react';
import { Search, Bell, RefreshCw, ChevronRight } from 'lucide-react';
import { useLocation } from 'react-router-dom';
import './Header.css';

export default function Header() {
  const location = useLocation();
  
  // Very simple breadcrumb logic for UI mock purposes
  const path = location.pathname;
  let pageName = 'System Overview';
  if (path === '/issues') pageName = 'Issues & Diagnostics';
  else if (path === '/monitor') pageName = 'Live System Monitor';
  else if (path === '/settings') pageName = 'Settings';
  
  return (
    <header className="main-header">
      <div className="breadcrumb flex items-center gap-2 text-sm">
        <span className="text-muted">Dashboard</span>
        <ChevronRight size={14} className="text-muted" />
        <span className="font-medium text-main">{pageName}</span>
      </div>
      
      <div className="header-actions flex items-center gap-4">
        <div className="search-bar flex items-center gap-2">
          <Search size={16} className="text-muted" />
          <input type="text" placeholder="Search..." className="search-input" />
        </div>
        
        <div className="flex items-center gap-3">
          <button className="icon-btn relative">
            <Bell size={20} className="text-secondary" />
            <span className="notification-dot"></span>
          </button>
          
          <div className="flex items-center gap-2 text-xs text-muted border-left pl-3">
            <span>Last updated: 2 mins ago</span>
            <button className="icon-btn-small">
              <RefreshCw size={14} />
            </button>
          </div>
        </div>
      </div>
    </header>
  );
}
