import React from 'react';
import "react-loader-spinner/dist/loader/css/react-spinner-loader.css";
import Loader from 'react-loader-spinner';

const LoaderPage = (): JSX.Element => (
    <div className='loader'>
        <Loader type="Watch" color="#333" height={80} width={80} />
    </div>
)

export default LoaderPage;