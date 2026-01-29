import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [message, setMessage] = useState("");
  
  useEffect(() => {
    fetch("/api/test")
      .then(response => response.text())
      .then(message => {
        setMessage(message);
      });
  }, [])

  return (
    <>
      <div className='App'>
        <header className='App-header'>
          <img src={reactLogo} className='App-logo' alt='logo' />
          <p>
            test : {message}
          </p>
        </header>
      </div>
    </>
  );
}

export default App
