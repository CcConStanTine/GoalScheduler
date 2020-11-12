import React, { useContext } from 'react';
import { darkModeText } from '../utils/variables';
import { FaToggleOn, FaToggleOff, FaMoon } from 'react-icons/fa';
import { ThemeContext } from '../authentication/ThemeContext';

const NightMode = () => {
    const { theme, setTheme } = useContext(ThemeContext);

    if (theme === 'darktheme') {
        document.getElementById('root')?.classList.add('darktheme');
        document.body.style.backgroundColor = "black";
    }

    else {
        document.body.style.backgroundColor = "white";
        document.getElementById('root')?.classList.remove('darktheme')
    }

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