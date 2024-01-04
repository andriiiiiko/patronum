import React from 'react';
import './App.css'
import Header from './Header';
import Footer from './Footer';
import { Outlet } from 'react-router-dom';


const Home = () => {
    return (
        <div className='App-layout'>
            <Header />
                <Outlet/>
            <Footer/>
        </div>
    );
};

export default Home;
