import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Dashboard from './pages/Dashboard';
import Issues from './pages/Issues';
import SystemMonitor from './pages/SystemMonitor';
import Settings from './pages/Settings';
import PlaceholderPage from './pages/PlaceholderPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Dashboard />} />
          <Route path="issues" element={<Issues />} />
          <Route path="monitor" element={<SystemMonitor />} />
          <Route path="settings" element={<Settings />} />
          
          {/* Placeholder routes for the rest of the sidebar */}
          <Route path="hardware" element={<PlaceholderPage title="Hardware Health" />} />
          <Route path="software" element={<PlaceholderPage title="Software Inventory" />} />
          <Route path="diagnostics" element={<PlaceholderPage title="Diagnostics" />} />
          <Route path="solutions" element={<PlaceholderPage title="Solutions" />} />
          <Route path="automation" element={<PlaceholderPage title="Automation Rules" />} />
          <Route path="reports" element={<PlaceholderPage title="System Reports" />} />
          <Route path="help" element={<PlaceholderPage title="Help & Support" />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
