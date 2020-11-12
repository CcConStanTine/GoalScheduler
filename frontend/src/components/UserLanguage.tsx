import React, { useContext } from 'react';
import { languageOptions, languagePack } from '../utils/variables';
import { FaGlobeEurope } from 'react-icons/fa';
import { LanguageContext } from '../authentication/LanguageContext'

const UserLanguage = () => {
    const { language, setLanguage } = useContext(LanguageContext);

    const changeLanguage = (value: string) => {
        setLanguage!(value);
        localStorage.setItem('language', value);
    }

    return (
        <div className='language'>
            <aside>
                <FaGlobeEurope className='language-icon' />
                <p>{languagePack[language].languageText}</p>
            </aside>
            <select value={language} onChange={({ target }) => changeLanguage(target.value)}>
                {languageOptions[language].map(({ value, label }) => <option key={value} value={value}>{label}</option>)}
            </select>
        </div>
    )
}

export default UserLanguage;