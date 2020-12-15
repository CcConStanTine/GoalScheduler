import React, { useState, useEffect, createContext, ReactNode, useContext } from 'react';
import { LoadingPageContext } from './LoadingPageContext';
import DataRequests from './DataRequests';
import LoaderPage from '../components/Loader';

interface AppContextInterface {
    userContext?: {
        email?: string,
        firstName?: string,
        lastName?: string,
        nick?: string,
        token?: string,
        userId?: number,
        userPhoto?: string
    };
    setLoggedIn?: (value: object) => void;
}

interface UseAppContextInterface {
    children: ReactNode;
}

export const AppContext = createContext<AppContextInterface>({});

export const UseAppContext = ({ children }: UseAppContextInterface) => {
    const { loading, setLoading } = useContext(LoadingPageContext);
    const [userContext, setLoggedIn] = useState({});

    useEffect(() => {
        const getUserData = async () => {
            const { token } = DataRequests.getCurrentUser();
            const userInfo = token && await DataRequests.getCurrentUserInfo();
            setLoggedIn({ token, ...userInfo });
            setLoading!(false);
        }
        getUserData()
    }, [setLoading]);

    return (
        <AppContext.Provider value={{ userContext, setLoggedIn }}>
            {loading && <LoaderPage />}
            {children}
        </AppContext.Provider>
    )
}