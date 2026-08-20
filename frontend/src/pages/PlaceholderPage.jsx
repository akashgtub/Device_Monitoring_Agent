import React from 'react';

export default function PlaceholderPage({ title }) {
  return (
    <div className="flex-col gap-6">
      <div className="flex items-center justify-between">
        <h2 className="text-xl font-semibold">{title}</h2>
      </div>
      <div className="card flex items-center justify-center p-12 text-muted">
        <p>This module is currently in development.</p>
      </div>
    </div>
  );
}
