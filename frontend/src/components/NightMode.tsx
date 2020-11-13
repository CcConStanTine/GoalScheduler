import React, { useContext } from 'react';
import languagePack from '../utils/languagePack';
import { FaToggleOn, FaToggleOff, FaMoon } from 'react-icons/fa';
import { ThemeContext } from '../authentication/ThemeContext';
import { LanguageContext } from '../authentication/LanguageContext';
import checkUsedTheme from './CheckUserTheme';

const NightMode = () => {
    const { theme, setTheme } = useContext(ThemeContext);
    const { language } = useContext(LanguageContext);

    checkUsedTheme(theme!);

    return (
        <div className='night-mode'>
            <aside>
                <FaMoon className='night-mode-icon' />
                <p>{languagePack[language].darkModeText}</p>
            </aside>
            {theme === 'darktheme' ?
                <FaToggleOn onClick={() => setTheme!('lightheme')} className="toggle-icon on" />
                :
                <FaToggleOff onClick={() => setTheme!('darktheme')} className="toggle-icon off" />}
        </div>
    )
}

export default NightMode;