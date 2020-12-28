import React, { useContext } from 'react';
import { LanguageContext } from '../../authentication/LanguageContext'
import NightMode from './NightMode';
import UserLanguage from './UserLanguage';
import ChangeUserCredentials from './ChangeUserCredentials';
import SignOut from './SignOut';
import { ChangeUserCredentialsTypes } from '../../utils/variables';
import languagePack from '../../utils/languagePack';

const UserSettings = () => {
    const { language } = useContext(LanguageContext);

    return (
        <div className='user-settings'>
            <NightMode />
            <UserLanguage />
            <ChangeUserCredentials
                type={ChangeUserCredentialsTypes.USERNAME}
                placeholder={languagePack[language].CHANGEUSERNAME.title}
                classType="username"
            />
            <ChangeUserCredentials
                type={ChangeUserCredentialsTypes.USERPASSWORD}
                placeholder={languagePack[language].CHANGEPASSWORD.title}
                classType="password"
            />
            <ChangeUserCredentials
                type={ChangeUserCredentialsTypes.EMAIL}
                placeholder={languagePack[language].CHANGEEMAIL.title}
                classType="email"
            />
            <ChangeUserCredentials
                type={ChangeUserCredentialsTypes.USERPHOTO}
                placeholder={languagePack[language].CHANGEUSERPHOTO.title}
                classType="photo"
            />
            <SignOut />
        </div>
    )
}

export default UserSettings;