import React, { useContext } from 'react';
import { darkModeText } from '../utils/variables';
import { FaToggleOn, FaToggleOff, FaMoon } from 'react-icons/fa';
import { ThemeContext } from '../authentication/ThemeContext';
import checkUsedTheme from './CheckUserTheme';

const NightMode = () => {
    const { theme, setTheme } = useContext(ThemeContext);

    checkUsedTheme(theme!);

    return (
        <div className='night-mode'>
            <aside>
                <FaMoon className='night-mode-icon' />
                <p>{darkModeText}</p>
            </aside>
            {theme === 'darktheme' ?
                <FaToggleOn onClick={() => setTheme!('lightheme')} className="toggle-icon on" />
                :
                <FaToggleOff onClick={() => setTheme!('darktheme')} className="toggle-icon off" />}
        </div>
    )
}

export default NightMode;