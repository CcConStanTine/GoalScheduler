import React, { createContext, useState, ReactNode } from 'react';

interface LoadingPageInterface {
    loading: Boolean;
    setLoading?: (value: Boolean) => void;
}


interface UseLoaderContextInterface {
    children: ReactNode;
}

export const LoadingPageContext = createContext<LoadingPageInterface>({ loading: true });

export const UseLoadingContext = ({ children }: UseLoaderContextInterface) => {
    const [loading, setLoading] = useState<Boolean>(true);

    return (
        <LoadingPageContext.Provider value={{ loading, setLoading }}>
            {children}
        </LoadingPageContext.Provider>
    )
}