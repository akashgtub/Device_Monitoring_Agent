import React from 'react';
import './DeviceConsentModal.css'; // Reusing modal base styles

const ActionApprovalModal = ({ recommendation, onApprove, onCancel }) => {
    if (!recommendation) return null;

    return (
        <div className="modal-overlay">
            <div className="modal-content glass-panel" style={{ borderLeft: '4px solid #ffaa00' }}>
                <h2>AI Auto-Fix Recommendation</h2>
                
                <div className="permission-group">
                    <h3>Problem Detected</h3>
                    <p style={{ color: 'var(--text-primary)', margin: '0 0 10px 0' }}>{recommendation.problem}</p>
                </div>

                <div className="permission-group" style={{ background: 'rgba(0, 200, 100, 0.1)' }}>
                    <h3>Recommended Action</h3>
                    <p style={{ color: 'var(--text-primary)', margin: '0 0 10px 0', fontWeight: 'bold' }}>
                        {recommendation.actionDescription}
                    </p>
                    <div style={{ display: 'flex', gap: '20px', marginTop: '10px', fontSize: '0.9rem' }}>
                        <div>
                            <span style={{ color: 'var(--text-secondary)' }}>Risk Level: </span>
                            <span style={{ color: recommendation.riskLevel === 'LOW' ? '#4caf50' : '#ff9800', fontWeight: 'bold' }}>
                                {recommendation.riskLevel}
                            </span>
                        </div>
                        <div>
                            <span style={{ color: 'var(--text-secondary)' }}>Expected Result: </span>
                            <span>{recommendation.expectedResult}</span>
                        </div>
                    </div>
                </div>

                <div className="modal-actions" style={{ gap: '10px' }}>
                    <button type="button" className="btn-secondary" onClick={onCancel} style={{
                        background: 'transparent', border: '1px solid var(--border-color)', color: 'var(--text-primary)', padding: '0.75rem 1.5rem', borderRadius: '6px', cursor: 'pointer'
                    }}>Cancel</button>
                    <button type="button" className="btn-primary" onClick={() => onApprove(recommendation.actionId)}>Approve & Execute</button>
                </div>
            </div>
        </div>
    );
};

export default ActionApprovalModal;
