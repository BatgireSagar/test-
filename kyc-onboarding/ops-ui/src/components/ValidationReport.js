import React from 'react';

const dummyReport = {
  status: 'manual_review_required',
  validated: ['Entity Name', 'Incorporation Country'],
  missing: ['UBO Declaration'],
  invalid: ['Expired Passport'],
  mismatched: ['Form Type: W8BEN instead of W8BEN-E'],
  auditTrail: [
    { step: 'validation', result: 'manual_review_required' }
  ]
};

export default function ValidationReport({ clientId }) {
  // TODO: Fetch real report by clientId
  const report = dummyReport;

  return (
    <div>
      <h3>Validation Report for {clientId}</h3>
      <p>Status: <b>{report.status}</b></p>
      <div>
        <strong>Validated:</strong> {report.validated.join(', ')}
      </div>
      <div>
        <strong>Missing:</strong> {report.missing.join(', ')}
      </div>
      <div>
        <strong>Invalid:</strong> {report.invalid.join(', ')}
      </div>
      <div>
        <strong>Mismatched:</strong> {report.mismatched.join(', ')}
      </div>
      <div>
        <strong>Audit Trail:</strong>
        <ul>
          {report.auditTrail.map((a, i) => (
            <li key={i}>{a.step}: {a.result}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}