import React, { useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

const RegistrationPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleRegistration = async () => {
        try {
            const response = await axios.post('http://localhost:80/api/v1/auth/register', {
                username,
                password,
                confirmPassword,
            });

            const { error } = response.data;

            if (error === 'OK') {
                console.log('Успешная регистрация');
                navigate('/login');
            } else if (error === 'USER_ALREADY_EXISTS') {
                setError('Такой пользователь уже зарегистрирован');
            } else if (error === 'INVALID_USERNAME') {
                setError('Имя пользователя пустое');
            } else if (error === 'INVALID_PASSWORD') {
                setError('Пароль должен быть не менее 8 символов');
            } else if (error === 'INVALID_CONFIRM_PASSWORD') {
                setError('Ваши пароли не совпадают');
            } else {
                setError('Произошла ошибка при регистрации');
                console.error('Ошибка регистрации:', error);
            }
        } catch (error) {
            console.error('Произошла неожиданная ошибка при регистрации', error.response?.data || error.message);
            setError('Произошла неожиданная ошибка при регистрации');
        }

    };

    return (
        <div className='App-container'>
            <h1 className='Auth-title'>Registration</h1>
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
                <label className='Form-label'>
                    <p>
                        Confirm Password:
                    </p>
                    <input
                        type="password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                </label>
                <br />
                <button type="button" onClick={handleRegistration} className='button'>
                    Register
                </button>
            </form>
            {error && <p>{error}</p>}
            <p className='Auth-text'>
                Уже есть аккаунт? <Link to="/login">Войдите</Link>.
            </p>
        </div>
    );
};

export default RegistrationPage;
