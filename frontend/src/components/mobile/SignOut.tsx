import React, { useContext } from 'react';
import DataRequests from '../../authentication/DataRequests';
import { AppContext } from '../../authentication/AppContext';
import { LanguageContext } from '../../authentication/LanguageContext';
import languagePack from '../../utils/languagePack';
import { FaSignOutAlt } from 'react-icons/fa';

const SignOut = () => {
    const { setLoggedIn } = useContext(AppContext);
    const { language } = useContext(LanguageContext);

    const logout = () => {
        DataRequests.logout();
        setLoggedIn!({});
    }

    return (
        <div className='sign-out'>
            <aside onClick={logout}>
                <FaSignOutAlt className='sign-out-icon' />
                <p>{languagePack[language].SETTINGS.signOut}</p>
            </aside>
        </div>
    )
}


export default SignOut