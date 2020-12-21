import React, { useState } from 'react';
import AccountForm from '../../../components/AccountForm';
import { AccountFormTypes } from '../../../utils/variables';

const WelcomePage = (): JSX.Element => {
    const [active, setActive] = useState('');

    return (
        <section className="welcome-page-desktop">
            <div className={`container ${active}`}>
                <div className="form-container sign-up-container">
                    <AccountForm type={AccountFormTypes.CREATE} headerSignUp='Create Account' mobile={false} />
                </div>
                <div className="form-container sign-in-container">
                    <AccountForm type={AccountFormTypes.LOGIN} headerLogIn='Sign In' mobile={false} />
                </div>
                <div className="overlay-container">
                    <div className="overlay">
                        <div className="overlay-panel overlay-left">
                            <h1>Welcome Back!</h1>
                            <p>To keep connected with us please login with your personal info</p>
                            <button className="desktop-default-button" onClick={() => setActive('')}>Sign In</button>
                        </div>
                        <div className="overlay-panel overlay-right">
                            <h1>Hello, Friend!</h1>
                            <p>Enter your personal details and start journey with us</p>
                            <p className='important'>MOBILE VIEW FINISHED, DESKTOP IN PROGRESS</p>
                            <button className="desktop-default-button" onClick={() => setActive("right-panel-active")}>Sign Up</button>
                        </div>
                    </div>
                </div>
            </div>
            <footer className={active}>
                <ul>
                    <li>Sign in</li>
                    <li>Create account</li>
                    <li>Features</li>
                    <li>Privacy & Safety</li>
                    <li>Contact</li>
                </ul>
            </footer>
        </section >
    )
}

export default WelcomePage;