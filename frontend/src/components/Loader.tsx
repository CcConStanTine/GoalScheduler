import React from 'react';
import "react-loader-spinner/dist/loader/css/react-spinner-loader.css";
import Loader from 'react-loader-spinner';

interface LoaderInterface {
    loadingProgress?: number;
}

const LoaderPage = ({ loadingProgress }: LoaderInterface): JSX.Element => (
    <div className='loader'>
        <Loader type="Watch" color="#333" height={80} width={80} />
        <p className="loading-progress">
            <span className="loading-bar" style={{ width: `${loadingProgress}%` }}></span>
            {console.log(loadingProgress)}
        </p>
    </div>
)

export default LoaderPage;