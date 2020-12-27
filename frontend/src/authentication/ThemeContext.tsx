import React, { createContext, useState, ReactNode } from 'react';
import { ApplicationThemeOptions } from '../utils/enums';

interface AppContextInterface {
    theme: ApplicationThemeOptions;
    setTheme?: (value: ApplicationThemeOptions) => void;
}


interface UseThemeContextInterface {
    children: ReactNode;
}

export const ThemeContext = createContext<AppContextInterface>({ theme: ApplicationThemeOptions.LIGHT });

export const UseThemeContext = ({ children }: UseThemeContextInterface) => {
    const [theme, setTheme] = useState(ApplicationThemeOptions.LIGHT);

    return (
        <ThemeContext.Provider value={{ theme, setTheme }}>
            {children}
        </ThemeContext.Provider>
    )
}