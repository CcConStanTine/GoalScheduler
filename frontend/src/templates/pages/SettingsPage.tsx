import React, { useContext, useState } from 'react';
import { Link } from 'react-router-dom';
import auth from '../../authentication/database';
import { darkModeText, signOutText, languageOptions, languageText, changePasswordText, changeUsernameText } from '../../utils/variables';
import { AppContext } from '../../authentication/AppContext';
import { FaChevronLeft, FaToggleOn, FaToggleOff, FaMoon, FaSignOutAlt, FaGlobeEurope, FaKey, FaUser } from 'react-icons/fa';

interface HomeInterface {
    history: any
}

const SettingsPage = ({ history }: HomeInterface) => {
    const { userContext, setLoggedIn } = useContext(AppContext);
    const [darkMode, setDarkMode] = useState(false);
    const [language, setLanguage] = useState('polish');

    const logout = () => {
        auth.signout();
        if (setLoggedIn) setLoggedIn({
            loggedIn: auth.isAuthenticated(),
        });
    }

    return (
        <section className='settings-page'>
            <nav className="navigation">
                <FaChevronLeft onClick={() => history.goBack()} />
            </nav>
            <div className='user-data'>
                <img src={userContext?.userPhoto} alt={`${userContext?.userName}`} />
                <p>{userContext?.userName}</p>
            </div>
            <div className='user-settings'>
                <div className='night-mode'>
                    <aside>
                        <FaMoon />
                        <p>{darkModeText}</p>
                    </aside>
                    {darkMode ?
                        <FaToggleOn onClick={() => setDarkMode(!darkMode)} className="toggle-icon on" />
                        :
                        <FaToggleOff onClick={() => setDarkMode(!darkMode)} className="toggle-icon off" />}
                </div>
                <div className='language'>
                    <aside>
                        <FaGlobeEurope />
                        <p>{languageText}</p>
                    </aside>
                    <select value={language} onChange={event => setLanguage(event.target.value)}>
                        {languageOptions.map(({ value, label }) => <option key={value} value={value}>{label}</option>)}
                    </select>
                </div>
                <div className='change-username'>
                    <aside>
                        <FaUser />
                        <Link to="/app/change-username">{changeUsernameText}</Link>
                    </aside>
                </div>
                <div className='change-password'>
                    <aside>
                        <FaKey />
                        <Link to="/app/change-password">{changePasswordText}</Link>
                    </aside>
                </div>
                <div className='sign-out'>
                    <aside onClick={logout}>
                        <FaSignOutAlt />
                        <p>{signOutText}</p>
                    </aside>
                </div>
            </div>
        </section>
    )
}

export default SettingsPage;