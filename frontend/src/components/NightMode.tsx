import React, { useState } from 'react';
import { darkModeText } from '../utils/variables';
import { FaToggleOn, FaToggleOff, FaMoon } from 'react-icons/fa';

const NightMode = () => {
    const [darkMode, setDarkMode] = useState(false);

    return (
        <div className='night-mode'>
            <aside>
                <FaMoon />
                <p>{darkModeText}</p>
            </aside>
            {darkMode ?
                <FaToggleOn onClick={() => setDarkMode(!darkMode)} className="toggle-icon on" />
                :
                <FaToggleOff onClick={() => setDarkMode(!darkMode)} className="toggle-icon off" />}
        </div>
    )
}

export default NightMode;