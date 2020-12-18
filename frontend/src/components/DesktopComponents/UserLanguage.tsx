import React, { useContext } from 'react';
import { languageOptions } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import { LanguageContext } from '../../authentication/LanguageContext';

const UserLanguage = () => {
    const { language, setLanguage } = useContext(LanguageContext);

    const changeLanguage = (value: string) => {
        setLanguage!(value);
        localStorage.setItem('language', value);
    }

    return (
        <div className='entry-container language'>
            <div>
                <h1>{languagePack[language].SETTINGS.language}</h1>
                <p>Change the language GoalScheduer is displayed in.
                         You must restart the application for the changes to take effect.</p>
            </div>
            <select value={language} onChange={event => changeLanguage(event.target.value)}>
                {languageOptions[language].map(({ text, value }) => <option value={value} key={text}>{text}</option>)}
            </select>
        </div>
    )
}

export default UserLanguage;