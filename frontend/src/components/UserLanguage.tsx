import React, { useState } from 'react';
import { languageText, languageOptions } from '../utils/variables';
import { FaGlobeEurope } from 'react-icons/fa';

const UserLanguage = () => {
    const [language, setLanguage] = useState('english');

    return (
        <div className='language'>
            <aside>
                <FaGlobeEurope />
                <p>{languageText}</p>
            </aside>
            <select value={language} onChange={event => setLanguage(event.target.value)}>
                {languageOptions.map(({ value, label }) => <option key={value} value={value}>{label}</option>)}
            </select>
        </div>
    )
}

export default UserLanguage;