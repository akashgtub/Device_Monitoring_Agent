import React from 'react';
import * as Icons from 'lucide-react';
import './MetricCard.css';

export default function MetricCard({ title, value, trend, trendValue, iconName }) {
  const Icon = Icons[iconName] || Icons.Activity;
  
  const isTrendUp = trend === 'up';
  const isTrendDown = trend === 'down';
  
  // Determine if trend is good or bad (simplification for mock)
  // For CPU/Mem/Disk/Temp, down is usually good.
  let trendClass = 'text-muted';
  if (isTrendDown) trendClass = 'text-success';
  if (isTrendUp) trendClass = 'text-critical';
  if (title === 'Network') {
    trendClass = isTrendUp ? 'text-success' : 'text-warning';
  }

  return (
    <div className="card metric-card">
      <div className="flex items-center justify-between mb-2">
        <div className="flex items-center gap-2 text-secondary">
          <Icon size={16} />
          <span className="text-sm font-medium">{title}</span>
        </div>
        <Icons.MoreHorizontal size={16} className="text-muted cursor-pointer" />
      </div>
      <div className="metric-value">{value}</div>
      <div className="flex items-center gap-1 mt-2 text-xs">
        {isTrendUp && <Icons.TrendingUp size={14} className={trendClass} />}
        {isTrendDown && <Icons.TrendingDown size={14} className={trendClass} />}
        {trend === 'stable' && <Icons.Minus size={14} className={trendClass} />}
        <span className={trendClass}>{trendValue}</span>
        <span className="text-muted">vs last hour</span>
      </div>
    </div>
  );
}
