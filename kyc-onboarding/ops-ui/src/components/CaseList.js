import React, { useEffect, useState } from 'react';

const dummyCases = [
  {
    clientId: 'C-2024-001',
    entityName: 'Sample Fund Ltd.',
    status: 'manual_review_required',
    lastUpdated: '2024-07-19T12:00:00Z'
  },
  {
    clientId: 'C-2024-002',
    entityName: 'Example Holdings',
    status: 'validated',
    lastUpdated: '2024-07-18T15:30:00Z'
  }
];

export default function CaseList({ onSelectCase }) {
  const [cases, setCases] = useState([]);

  useEffect(() => {
    // TODO: Replace with API call to fetch cases
    setCases(dummyCases);
  }, []);

  return (
    <div>
      <h2>Onboarding Cases</h2>
      <table>
        <thead>
          <tr>
            <th>Client ID</th>
            <th>Entity Name</th>
            <th>Status</th>
            <th>Last Updated</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {cases.map((c) => (
            <tr key={c.clientId}>
              <td>{c.clientId}</td>
              <td>{c.entityName}</td>
              <td>{c.status}</td>
              <td>{new Date(c.lastUpdated).toLocaleString()}</td>
              <td>
                <button onClick={() => onSelectCase(c)}>View</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}