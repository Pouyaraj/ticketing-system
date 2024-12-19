import React from 'react';
import { Route, Routes } from 'react-router-dom';
import './App.css';
import HomeFile from './Component/Home/HomeFile';
import NavBar from './Component/NavBar/NavBarFile';
import RegisterFile from './Component/Register/RegisterFile';
import LoginForm from './Component/Login/LoginForm';
import EmployeeAccountFile from './Component/EmployeeAccount/EmployeeAccountFile';
import CreateTicketsFile from './Component/CreateTickets/CreateTicketsFile';
import ViewTicketsFile from './Component/ViewTickets/ViewTicketsFile';
import ManagerAccount from './Component/ManagerAccount/ManagerAccount';

function App() {
  return (
    <div className="App">
      <NavBar/>
      <Routes>
        <Route path='/' element={<HomeFile/>}></Route>
        <Route path='/register' element={<RegisterFile/>}></Route>
        <Route path='/login' element={<LoginForm/>}></Route>
        <Route path='/employee-account' element={<EmployeeAccountFile/>}></Route>
        <Route path='/create-ticket' element={<CreateTicketsFile/>}></Route>
        <Route path='/view-ticket' element={<ViewTicketsFile/>}></Route>
        <Route path='/manager-account' element={<ManagerAccount/>}></Route>
      </Routes>
    </div>
  );
}

export default App;
