import React, { useContext } from 'react';
import { ThemeContext } from '../../authentication/ThemeContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import checkUsedTheme from './../CheckUserTheme';
import { ApplicationThemeOptions } from '../../utils/enums';
import languagePack from '../../utils/languagePack';

const NightMode = (): JSX.Element => {
    const { theme, setTheme } = useContext(ThemeContext);
    const { language } = useContext(LanguageContext);

    checkUsedTheme(theme);

    return (
        <div className='entry-container dark-mode'>
            <div>
                <h1>{languagePack[language].SETTINGS.darkMode}</h1>
                <p>{languagePack[language].SETTINGS.themeInfo}</p>
            </div>
            <div className="switcher" onClick={() => setTheme!(theme === ApplicationThemeOptions.DARK ? ApplicationThemeOptions.LIGHT : ApplicationThemeOptions.DARK)}>
                <div className={`toggler ${theme}`}></div>
            </div>
        </div>
    )
}

export default NightMode;