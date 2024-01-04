import React from 'react';
import './App.css'

const Header = () => {
    return (
        <div className='App-header'>
            <a href='/' className='App-link'>URL SHORTER</a>
            <ul className='buttons'>
                <li>
                    <a href="/about">
                        <button className='button'>
                            About
                        </button>
                    </a>
                </li>
                <li>
                    <a href="/login">
                        <button className='button'>
                            Login
                        </button>
                    </a>
                </li>
                <li>
                    <a href="/register">
                        <button className='button'>
                            Register
                        </button>
                    </a>
                </li>
                <li>
                    <a href="/viewAll">
                        <button className='button'>
                            View All
                        </button>
                    </a>
                </li>
            </ul>
        </div>
    )

}

export default Header;