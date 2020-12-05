import React, { useContext } from 'react';
import languagePack from '../utils/languagePack';
import { FaMoon } from 'react-icons/fa';
import { ThemeContext } from '../authentication/ThemeContext';
import { LanguageContext } from '../authentication/LanguageContext';
import checkUsedTheme from './CheckUserTheme';

const NightMode = () => {
    const { theme, setTheme } = useContext(ThemeContext);
    const { language } = useContext(LanguageContext);

    checkUsedTheme(theme!);

    return (
        <div className='night-mode' onClick={() => setTheme!(theme === "darktheme" ? "lighttheme" : "darktheme")}>
            <aside>
                <FaMoon className='night-mode-icon' />
                <p>{languagePack[language].SETTINGS.darkMode}</p>
            </aside>
            <div className="switcher">
                <div className={`toggler ${theme}`}></div>
            </div>
        </div>
    )
}

export default NightMode;