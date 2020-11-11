import React, { useState, useContext } from 'react';
import {
    appLogo,
    appLogoDescription,
    appName,
    appWelcomeMessage,
    continueWithoutSigningInMessage,
    AccountFormTypes,
    appWelcomeSpanMessage,
    signUp,
    loginIn
} from './../../utils/variables';
import { AppContext } from '../../authentication/AppContext';
import AccountForm from '../../components/AccountForm';
import { landingPageInterface } from '../../utils/interfaces';
import { Redirect } from 'react-router-dom'

const WelcomePage = ({ history }: landingPageInterface) => {
    const [showRegisterPanel, setShowRegisterPanel] = useState(false);
    const { userContext } = useContext(AppContext);

    if (userContext?.token) return <Redirect to="/app/home" />

    return (
        <section className="welcome-page">
            <nav className="navigation">
                <img src={appLogo} alt={appLogoDescription} />
                <p>{appName}</p>
            </nav>
            <main className="main">
                <div className="welcome-message">
                    <h1>{appWelcomeMessage}</h1>
                </div>
                <div className="login-or-register-buttons">
                    <button onClick={() => setShowRegisterPanel(true)}>{signUp}</button>
                    <button onClick={() => setShowRegisterPanel(false)}>{loginIn}</button>
                </div>
                <div className="form-container">
                    {showRegisterPanel ?
                        <AccountForm type={AccountFormTypes.CREATE} history={history} />
                        :
                        <AccountForm type={AccountFormTypes.LOGIN} history={history} />
                    }
                </div>
                <div className="login-without-register">
                    <span>{appWelcomeSpanMessage}</span>
                    <button>{continueWithoutSigningInMessage}</button>
                </div>
            </main>
        </section >
    )
}

export default WelcomePage;