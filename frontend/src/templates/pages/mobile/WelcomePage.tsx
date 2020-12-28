import React, { useContext } from 'react';
import { appLogo, appName } from '../../../utils/variables';
import languagePack from '../../../utils/languagePack';
import AccountForm from '../../../components/mobile/AccountForm';
import { LanguageContext } from '../../../authentication/LanguageContext';
import { AccountFormTypes } from '../../../utils/enums';

interface WelcomePageMobile {
    showRegisterPanel: boolean,
    setShowRegisterPanel: (value: boolean) => void
}

const WelcomePage = ({ showRegisterPanel, setShowRegisterPanel }: WelcomePageMobile): JSX.Element => {
    const { language } = useContext(LanguageContext);

    return (
        <section className="welcome-page">
            <nav className="navigation">
                <img src={appLogo} alt={languagePack[language].GLOBAL.appName} />
                <p>{appName}</p>
            </nav>
            <main className="main">
                <div className="welcome-message">
                    <h1>{languagePack[language].WELCOME.appDescription}</h1>
                </div>
                <div className="login-or-register-buttons">
                    <button onClick={() => setShowRegisterPanel(true)}>{languagePack[language].WELCOME.signUp}</button>
                    <button onClick={() => setShowRegisterPanel(false)}>{languagePack[language].WELCOME.logIn}</button>
                </div>
                <div className="form-container">
                    {showRegisterPanel ?
                        <AccountForm type={AccountFormTypes.CREATE} />
                        :
                        <AccountForm type={AccountFormTypes.LOGIN} />
                    }
                </div>
                <div className="login-without-register">
                    <span>{languagePack[language].WELCOME.or}</span>
                    <button>{languagePack[language].WELCOME.continueWithoutSigningIn}</button>
                </div>
            </main>
        </section >
    )
}

export default WelcomePage;