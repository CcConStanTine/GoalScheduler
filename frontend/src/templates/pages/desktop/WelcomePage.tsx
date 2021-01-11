import React, { useContext, useState } from 'react';
import { LanguageContext } from '../../../authentication/LanguageContext';
import AccountForm from '../../../components/mobile/AccountForm';
import { AccountFormTypes, WelcomePageDesktopActiveTypes } from '../../../utils/enums';
import languagePack from '../../../utils/languagePack';

const WelcomePage = (): JSX.Element => {
    const [active, setActive] = useState<WelcomePageDesktopActiveTypes>(WelcomePageDesktopActiveTypes.SIGNIN);
    const { language } = useContext(LanguageContext);

    return (
        <section className="welcome-page-desktop">
            <div className={`container ${active}`}>
                <div className="form-container sign-up-container">
                    <AccountForm type={AccountFormTypes.CREATE} headerSignUp={languagePack[language].WELCOME.createAccount} mobile={false} />
                </div>
                <div className="form-container sign-in-container">
                    <AccountForm type={AccountFormTypes.LOGIN} headerLogIn={languagePack[language].WELCOMEPAGEDESKTOP.SIGNUP.title} mobile={false} />
                </div>
                <div className="overlay-container">
                    <div className="overlay">
                        <div className="overlay-panel overlay-left">
                            <h1>{languagePack[language].WELCOMEPAGEDESKTOP.SIGNIN.header}</h1>
                            <p>{languagePack[language].WELCOMEPAGEDESKTOP.SIGNIN.info}</p>
                            <button className="desktop-default-button" onClick={() => setActive(WelcomePageDesktopActiveTypes.SIGNIN)}>
                                {languagePack[language].WELCOMEPAGEDESKTOP.SIGNUP.title}
                            </button>
                        </div>
                        <div className="overlay-panel overlay-right">
                            <h1>{languagePack[language].WELCOMEPAGEDESKTOP.SIGNUP.header}</h1>
                            <p>{languagePack[language].WELCOMEPAGEDESKTOP.SIGNUP.info}</p>
                            <button className="desktop-default-button" onClick={() => setActive(WelcomePageDesktopActiveTypes.SIGNUP)}>
                                {languagePack[language].WELCOMEPAGEDESKTOP.SIGNIN.title}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <footer className={active}>
                <ul>
                    <li onClick={() => setActive(WelcomePageDesktopActiveTypes.SIGNIN)}>{languagePack[language].WELCOMEPAGEDESKTOP.SIGNUP.title}</li>
                    <li onClick={() => setActive(WelcomePageDesktopActiveTypes.SIGNUP)}>{languagePack[language].WELCOME.createAccount}</li>
                    <li>{languagePack[language].WELCOME.features}</li>
                    <li>{languagePack[language].WELCOME.privacyAndSafety}</li>
                    <li>{languagePack[language].WELCOME.contact}</li>
                </ul>
            </footer>
        </section >
    )
}

export default WelcomePage;