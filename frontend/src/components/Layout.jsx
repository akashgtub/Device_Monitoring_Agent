import React from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from './Sidebar';
import Header from './Header';

export default function Layout() {
  return (
    <div className="app-layout">
      <Sidebar />
      <div className="main-content-wrapper">
        <Header />
        <main className="main-scroll-area">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
