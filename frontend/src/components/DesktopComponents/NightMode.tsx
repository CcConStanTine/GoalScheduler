import React, { useContext } from 'react';
import languagePack from '../../utils/languagePack';
import { ThemeContext } from '../../authentication/ThemeContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import checkUsedTheme from './../CheckUserTheme';

const NightMode = () => {
    const { theme, setTheme } = useContext(ThemeContext);
    const { language } = useContext(LanguageContext);

    checkUsedTheme(theme!);

    return (
        <div className='entry-container dark-mode'>
            <div>
                <h1>{languagePack[language].SETTINGS.darkMode}</h1>
                <p>Change theme of the application. In the future there will be more options.</p>
            </div>
            <div className="switcher" onClick={() => setTheme!(theme === "darktheme" ? "lighttheme" : "darktheme")}>
                <div className={`toggler ${theme}`}></div>
            </div>
        </div>
    )
}

export default NightMode;