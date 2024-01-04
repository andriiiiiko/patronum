import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

const AuthPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [authToken, setAuthToken] = useState('');
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const response = await axios.post('http://localhost:80/api/v1/auth/login', {
                username,
                password,
            });

            const { error, authToken } = response.data;

            if (error === 'OK'  && authToken) {
                // Вход выполнен успешно
                console.log('Успешный вход', authToken);
                setAuthToken(authToken);
                localStorage.setItem('authToken', authToken);
                navigate('/userview');
            } else if (error === 'INVALID_USER_NAME') {
                setError('Имя пользователя пустое');
            } else if (error === 'INVALID_PASSWORD') {
                setError('Пароль должен быть не менее 8 символов');
            }
        } catch (error) {
            console.error('Произошла неожиданная ошибка при авторизации', error.response?.data || error.message);
            setError('Произошла неожиданная ошибка при авторизации');
        }
    };

    return (
        <div className='App-container'>
            <h1 className='Auth-title'>Login</h1>
            <form className='Form'>
                <label className='Form-label'>
                    <p>
                        Username:
                    </p>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </label>
                <br />
                <label className='Form-label'>
                    <p>
                        Password:
                    </p>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </label>
                <br />
                <button type="button" onClick={handleLogin} className='button'>
                    Login
                </button>
            </form>
            {error && <p>{error}</p>}
            <p className='Auth-text'>
                Нет аккаунта? <Link to="/register">Зарегистрируйтесь</Link>.
            </p>
        </div>
    );
};

export default AuthPage;