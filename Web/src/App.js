import React from 'react';
import { BrowserRouter as Router, Route, Routes, } from "react-router-dom";

import Home from './components/Home/Home';
import Camera from './components/Camera/Camera';
import SignInPage from './components/Signin/SignIn';
import Login from './components/Login/Login';
import NotFound from './components/NotFound/NotFound';
import NavigationBar from './components/Nav/Navbar';
import Stat from './components/Statistics/Statistics';
import Profile from './components/Profile/Profile';
import ReviseInfo from './components/Profile/ReviseInfo';
import './styles/App.css';


function App() {
  return (
    <div className='App'>
      <Router>
        <NavigationBar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/Camera" element={<Camera />} />
          <Route path="/NotFound" element={<NotFound />} />
          <Route path="/SignIn" element={<SignInPage />} />
          <Route path="/Login" element={<Login />} />
          <Route path="/Statistics" element={<Stat />} />
          <Route path="/Profile" element={<Profile />} />
          <Route path="/ReviseInfo" element={<ReviseInfo />} />
        </Routes>
      </Router>
    </div>
  )
}

export default App;