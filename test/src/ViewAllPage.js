import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { BsEye } from "react-icons/bs";

const ViewAllPage = () => {
    const [data, setData] = useState([]);
    const [error, setError] = useState('');

    const handleRedirectClick = async (shortUrl) => {
        try {
            const response = await axios.post('http://localhost:80/api/v1/urls/view/redirect', { shortUrl });
            const { error, originalUrl } = response.data;

            if (error === 'OK') {
                window.open(originalUrl, '_blank'); // Открыть новую вкладку с оригинальным URL
            } else {
                setError('Error fetching redirect data');
            }
        } catch (error) {
            console.error('Unexpected error while fetching redirect data', error);
            setError('Unexpected error while fetching redirect data');
        }
    };

    const formatExpirationDate = (expirationDate) => {
        const dateObject = new Date(expirationDate);

        const day = String(dateObject.getDate()).padStart(2, '0');
        const month = String(dateObject.getMonth() + 1).padStart(2, '0'); // Добавляем 1, так как месяцы начинаются с 0
        const year = dateObject.getFullYear();
        const hours = String(dateObject.getHours()).padStart(2, '0');
        const minutes = String(dateObject.getMinutes()).padStart(2, '0');

        const formattedDate = `${hours}:${minutes}   ${day}/${month}/${year}`;

        return formattedDate;
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:80/api/v1/urls/view/all');
                const { error, urls } = response.data;

                if (error === 'OK') {
                    setData(urls);
                } else {
                    setError('Error fetching data');
                }
            } catch (error) {
                console.error('Unexpected error while fetching data', error);
                setError('Unexpected error while fetching data');
            }
        };

        fetchData();
    }, []);

    return (
        <div className='App-container'>
            <h1 className='Auth-title'>View All</h1>
            {error && <p>{error}</p>}

            <div className='Info-title Form-label'>
                <p>ShortUrl</p>
                <p>Time</p>
            </div>
            {data.map((item) => (
                <div key={item.id} className='Info-all'>
                    <div className='info-text'>
                        <p>
                            <a href={item.originalUrl} onClick={() => handleRedirectClick(item.shortUrl)}>
                                {/*//блокирует редирект */}
                                {item.shortUrl}
                            </a>
                        </p>
                        <p><BsEye/> {item.visitCount}</p>
                    </div>
                    <div className='info-text'>
                        <span><a href={item.expirationDate}>{formatExpirationDate(item.expirationDate)}</a></span>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default ViewAllPage;

