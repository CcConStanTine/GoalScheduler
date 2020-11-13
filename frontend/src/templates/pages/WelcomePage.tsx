import React, { useState, useContext } from 'react';
import { appLogo, appName, AccountFormTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';
import { AppContext } from '../../authentication/AppContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import AccountForm from '../../components/AccountForm';
import { landingPageInterface } from '../../utils/interfaces';
import { Redirect } from 'react-router-dom'

const WelcomePage = ({ history }: landingPageInterface) => {
    const [showRegisterPanel, setShowRegisterPanel] = useState(false);
    const { userContext } = useContext(AppContext);
    const { language } = useContext(LanguageContext);

    if (userContext?.token) return <Redirect to="/app/home" />

    return (
        <section className="welcome-page">
            <nav className="navigation">
                <img src={appLogo} alt={languagePack[language].appLogoDescription} />
                <p>{appName}</p>
            </nav>
            <main className="main">
                <div className="welcome-message">
                    <h1>{languagePack[language].appWelcomeMessage}</h1>
                </div>
                <div className="login-or-register-buttons">
                    <button onClick={() => setShowRegisterPanel(true)}>{languagePack[language].signUp}</button>
                    <button onClick={() => setShowRegisterPanel(false)}>{languagePack[language].loginIn}</button>
                </div>
                <div className="form-container">
                    {showRegisterPanel ?
                        <AccountForm type={AccountFormTypes.CREATE} history={history} />
                        :
                        <AccountForm type={AccountFormTypes.LOGIN} history={history} />
                    }
                </div>
                <div className="login-without-register">
                    <span>{languagePack[language].appWelcomeSpanMessage}</span>
                    <button>{languagePack[language].continueWithoutSigningInMessage}</button>
                </div>
            </main>
        </section >
    )
}

export default WelcomePage;