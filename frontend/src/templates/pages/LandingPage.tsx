import React from 'react';
import { Link } from 'react-router-dom';
import auth from '../../authentication/auth';
import AppLogo from '../../images/app_logo.png';

interface LandingInterface {
    history: any
}

const LandingPage = ({ history }: LandingInterface) => {
    const handleClick = () => auth.login(() => history.push("./app"));

    return (
        <div className="landing-page-wrapper">
            <section className="main-view">
                <nav>
                    <div className="image-and-logo">
                        <img src={AppLogo} alt="app_logo" />
                        <p>ScheduleApp</p>
                    </div>
                    <ul>
                        <Link to="#s">News</Link>
                        <Link to="#s">About</Link>
                        <Link to="#dd">Privacy and Security</Link>
                        <Link to="#ss">Contact</Link>
                    </ul>
                    <div className="login-or-register">
                        <p>Login</p>
                        <p>Register</p>
                    </div>
                </nav>
                <main>
                    <p>ScheduleApp</p>
                </main>
            </section>
            <h1>LandingPage</h1>
            <button onClick={handleClick}>Login</button>
        </div>
    )
}

export default LandingPage;