import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

const App = () => {
  const [yamlFile, setYamlFile] = useState(null);
  const [csvFile, setCsvFile] = useState(null);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  const handleYamlChange = (event) => {
    setYamlFile(event.target.files[0]);
  };

  const handleCsvChange = (event) => {
    setCsvFile(event.target.files[0]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!yamlFile || !csvFile) {
      setMessage('Please select both YAML and CSV files.');
      return;
    }

    const formData = new FormData();
    formData.append('yamlFile', yamlFile);
    formData.append('csvFile', csvFile);

    setLoading(true);
    setMessage('');

    try {
      const response = await axios.post('http://localhost:8080/api/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      setMessage(response.data);
    } catch (error) {
      setMessage('Error uploading files: ' + error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1>Upload Files</h1>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="yamlFile">Upload YAML File:</label>
          <input type="file" id="yamlFile" accept=".yaml" onChange={handleYamlChange} />
        </div>
        <div className="form-group">
          <label htmlFor="csvFile">Upload CSV File:</label>
          <input type="file" id="csvFile" accept=".csv" onChange={handleCsvChange} />
        </div>
        <button type="submit" disabled={loading}>
          {loading ? <div className="spinner"></div> : 'Submit'}
        </button>
      </form>
      {message && <p className="message">{message}</p>}
    </div>
  );
};

export default App;
