import React, { createContext, useState, ReactNode, useEffect } from 'react';
import languagePack from '../utils/languagePack';

interface AppContextInterface {
    language: string;
    setLanguage?: (value: string) => void;
}


interface UseLanguageContextInterface {
    children: ReactNode;
}

export const LanguageContext = createContext<AppContextInterface>({ language: 'en-US' });

export const UseLanguageContext = ({ children }: UseLanguageContextInterface) => {
    const [language, setLanguage] = useState('en-US');

    useEffect(() => {
        const getUserLanguage = () => {
            const defaultLanguage = window.navigator.language

            if (localStorage.getItem('language')) {
                const language = localStorage.getItem('language')!;

                if (languagePack[language])
                    setLanguage(language);
                else
                    setLanguage(defaultLanguage);
            }
            else
                setLanguage(defaultLanguage);
        }
        getUserLanguage()
    }, [])

    return (
        <LanguageContext.Provider value={{ language, setLanguage }}>
            {children}
        </LanguageContext.Provider>
    )
}