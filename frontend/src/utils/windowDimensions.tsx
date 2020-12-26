import { useState, useEffect } from 'react';
import { ReturnWindowDimension } from './interfaces';

const getWindowDimensions = (): ReturnWindowDimension => {
    return {
        width: window.innerWidth
    }
};

const useWindowDimensions = (): ReturnWindowDimension => {
    const [windowDimensions, setWindowDimensions] = useState<ReturnWindowDimension>(getWindowDimensions());

    useEffect(() => {
        const handleResize = () => setWindowDimensions(getWindowDimensions());

        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    return windowDimensions;
}

export default useWindowDimensions;