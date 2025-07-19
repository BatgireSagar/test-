import React, { useState } from 'react';
import CaseList from './components/CaseList';
import ValidationReport from './components/ValidationReport';

function App() {
  const [selectedCase, setSelectedCase] = useState(null);

  return (
    <div style={{ padding: 24 }}>
      <h1>KYC Ops Analyst Dashboard</h1>
      <CaseList onSelectCase={setSelectedCase} />
      {selectedCase && (
        <div style={{ marginTop: 32 }}>
          <ValidationReport clientId={selectedCase.clientId} />
        </div>
      )}
    </div>
  );
}

export default App;
