import React, { useContext } from 'react';
import { LanguageContext } from '../authentication/LanguageContext'
import NightMode from './NightMode';
import UserLanguage from './UserLanguage';
import ChangeUserCredentials from './ChangeUserCredentials';
import SignOut from './SignOut';
import { ChangeUserCredentialsTypes } from '../utils/variables';
import languagePack from '../utils/languagePack';

const UserSettings = () => {
    const { language } = useContext(LanguageContext);

    return (
        <div className='user-settings'>
            <NightMode />
            <UserLanguage />
            <ChangeUserCredentials
                type={ChangeUserCredentialsTypes.USERNAME}
                placeholder={languagePack[language].changeUsernameText}
                classType="username"
            />
            <ChangeUserCredentials
                type={ChangeUserCredentialsTypes.USERPASSWORD}
                placeholder={languagePack[language].changePasswordText}
                classType="password"
            />
            <ChangeUserCredentials
                type={ChangeUserCredentialsTypes.EMAIL}
                placeholder={languagePack[language].changeUserEmail}
                classType="email"
            />
            <SignOut />
        </div>
    )
}

export default UserSettings;