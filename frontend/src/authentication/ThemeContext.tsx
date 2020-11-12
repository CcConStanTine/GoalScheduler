import React, { createContext, useState, ReactNode } from 'react';

interface AppContextInterface {
    theme?: string;
    setTheme?: (value: string) => void;
}


interface UseThemeContextInterface {
    children: ReactNode;
}

export const ThemeContext = createContext<AppContextInterface>({});

export const UseThemeContext = ({ children }: UseThemeContextInterface) => {
    const [theme, setTheme] = useState('light');

    return (
        <ThemeContext.Provider value={{ theme, setTheme }}>
            {children}
        </ThemeContext.Provider>
    )
}