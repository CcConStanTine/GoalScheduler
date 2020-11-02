import React, { useState, useEffect, createContext, ReactNode } from 'react';
import auth from './database';

interface AppContextInterface {
    userContext?: {
        loggedIn?: any;
        tokenID?: string;
        userID?: number;
    };
    setLoggedIn?: (value: object) => void;
}

interface UseAppContextInterface {
    children: ReactNode;
}

export const AppContext = createContext<AppContextInterface>({});

export const UseAppContext = ({ children }: UseAppContextInterface) => {
    const [userContext, setLoggedIn] = useState({});

    useEffect(() => setLoggedIn({
        loggedIn: auth.isAuthenticated()
    }), []);

    return (
        <AppContext.Provider value={{ userContext, setLoggedIn }}>
            {children}
        </AppContext.Provider>
    )
}