// ViewAllPage.js
import React, {useCallback, useEffect, useState} from 'react';
import axios from 'axios';
import { BsEye } from "react-icons/bs";
// import dataArr from './test.json'


const UserView = () => {
    const [data, setData] = useState([]);
    const [error, setError] = useState('');
    const [selectedOption, setSelectedOption] = useState(null);
    // const [filteredData, setFilteredData] = useState([]);
    const [originalUrl, setOriginalUrl] = useState('');
    const [description, setDescription] = useState('');
    const fetchData = useCallback(async () => {

        try {
            const authToken = localStorage.getItem('authToken');
            const headers = { Authorization: `Bearer ${authToken}` };
            let url = 'http://localhost:80/api/v1/urls/view/all/user';
            if (selectedOption === 'option2') {
                url = 'http://localhost:80/api/v1/urls/view/all/user/active';
            }
            const response = await axios.get(url, {headers});
            const { error, userUrls } = response.data;

            if (error === 'OK') {
                setData(userUrls);
                // setFilteredData(userUrls);
            } else {
                setError('Error fetching data');
            }
        } catch (error) {
            console.error('Unexpected error while fetching data', error);
            setError('Unexpected error while fetching data');
        }
    },[selectedOption]);
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


    useEffect(() => {

        fetchData();
    }, [fetchData]);

    const handleConvert = async () => {
        try {
            const authToken = localStorage.getItem('authToken');
            const headers = { Authorization: `Bearer ${authToken}` };

            const requestData = {
                originalUrl,
                description,
            };

            const response = await axios.post('http://localhost:80/api/v1/urls/create', requestData, { headers });

            const { error, newUrl } = response.data;

            if (error === 'OK') {
                // Обработка успешного создания URL-ссылки, если необходимо
                console.log('URL created successfully:', newUrl);
            } else {
                setError('Error creating URL');
            }
        } catch (error) {
            console.error('Unexpected error while creating URL', error);
            setError('Unexpected error while creating URL');
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


    const handleRadioChange = (value) => {
        setSelectedOption(value);
        // сюда при смене радио кнопки надо повесить обработку фильтра с бека
        // запросом или как то еще
        fetchData();
    };

    const handleDelete = async (id) => {
        try {
            const authToken = localStorage.getItem('authToken');
            const headers = { Authorization: `Bearer ${authToken}` };

            const response = await axios.post(`http://localhost:80/api/v1/urls/delete/${id}`, {}, { headers });

            const { error } = response.data;

            if (error === 'OK') {
                // Обработка успешного удаления ссылки, если необходимо
                console.log('URL deleted successfully');
                // После удаления обновите данные, вызвав fetchData()
                fetchData();
            } else {
                setError('Error deleting URL');
            }
        } catch (error) {
            console.error('Unexpected error while deleting URL', error);
            setError('Unexpected error while deleting URL');
        }
    };

    const handleTime = async (shortUrl) => {
        try {
            const authToken = localStorage.getItem('authToken');
            const headers = { Authorization: `Bearer ${authToken}` };

            const response =  await axios.post('http://localhost:80/api/v1/extension', { shortUrl }, { headers });
            const { error } = response.data;

            if (error === 'OK') {
                // Обработка успешного удаления ссылки, если необходимо
                console.log('URL deleted successfully');
                // После удаления обновите данные, вызвав fetchData()
                await fetchData();
            } else {
                setError('Error deleting URL');
            }
        } catch (error) {
            console.error('Unexpected error while deleting URL', error);
            setError('Unexpected error while deleting URL');
        }
    };

    console.log('data', data)
    const isAuthToken = localStorage.getItem('authToken')
    return (
        <div className='App-container'>
            <h1 className='Auth-title'>View All</h1>
            {error && <p>{error}</p>}
            {isAuthToken ?
                <>
                    <div className='Convert-block'>
                        <form className='Form'>
                            <label className='Form-label'>
                                <p>
                                    Original Url:
                                </p>
                                <input
                                    type="text"
                                    value={originalUrl}
                                    onChange={(e) => setOriginalUrl(e.target.value)}
                                />
                            </label>
                            <br />
                            <label className='Form-label'>
                                <p>
                                    Description:
                                </p>
                                <input
                                    type="text"
                                    value={description}
                                    onChange={(e) => setDescription(e.target.value)}
                                />
                            </label>
                            <br />
                        </form>
                        <button type="button" onClick={handleConvert} className='button'>
                            Convert
                        </button>
                    </div>
                    <div className='radio-block'>
                        <label className="radio-label">
                            <input
                                type="radio"
                                name="radioGroup"
                                value="option1"
                                checked={selectedOption === 'option1'}
                                onChange={() => handleRadioChange('option1')}
                            />
                            user urls
                        </label>
                        <label className="radio-label">
                            <input
                                type="radio"
                                name="radioGroup"
                                value="option2"
                                checked={selectedOption === 'option2'}
                                onChange={() => handleRadioChange('option2')}
                            />
                            active urls
                        </label>
                    </div>
                    <>
                        <div className='Info-title Form-label'>
                            <p>ShortUrl</p>
                            <p>Time</p>
                        </div>
                        {data.map((item) => (
                            <div key={item.id} className='Info-all'>
                                <div className='info-text'>
                                    <p><a href={item.originalUrl} onClick={() => handleRedirectClick(item.shortUrl)}>
                                        {/*блокирует редирект /*/}
                                         {item.shortUrl}
                                    </a></p>
                                    <p><BsEye/> {item.visitCount}</p>
                                </div>
                                <div className='info-text'>
                                    <span><a href={item.expirationDate}>{formatExpirationDate(item.expirationDate)}</a></span>
                                </div>
                                <button type='button' onClick={() => handleTime(item.shortUrl)} className='button'>
                                    +30 Day
                                </button>
                                <button type='button' onClick={() => handleDelete(item.id)} className='button'>
                                    delete
                                </button>
                            </div>
                        ))}
                    </>
                </>
                :
                <>
                    {/*<div className='Info-title Form-label'>*/}
                {/*        <p>ShortUrl</p>*/}
                {/*        <p>OriginalUrl</p>*/}
                {/*</div>*/}
                {/*    {data.map((item) => (*/}
                {/*        <div key={item.id} className='Info-all'>*/}
                {/*            <div className='info-text'>*/}
                {/*                <p><a href={item.shortUrl}>{item.shortUrl}</a></p>*/}
                {/*                <p><BsEye /> {item.visitCount}</p>*/}
                {/*            </div>*/}
                {/*            <div className='info-text'>*/}
                {/*                <span><a href={item.originalUrl}>{item.originalUrl}</a></span>*/}
                {/*            </div>*/}
                {/*        </div>*/}
                {/*    ))}*/}
                </>
            }
        </div>
    );
};

export default UserView;
