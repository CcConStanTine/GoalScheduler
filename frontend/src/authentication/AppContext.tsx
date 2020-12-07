import React, { useState, useEffect, createContext, ReactNode } from 'react';
import DataRequests from './DataRequests';

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
    const [userContext, setLoggedIn] = useState({});

    useEffect(() => {
        const getUserData = async () => {
            const { token } = DataRequests.getCurrentUser();
            const userInfo = token && await DataRequests.getCurrentUserInfo();
            setLoggedIn({ token, ...userInfo })
        }
        getUserData()
    }, []);

    return (
        <AppContext.Provider value={{ userContext, setLoggedIn }}>
            {children}
        </AppContext.Provider>
    )
}