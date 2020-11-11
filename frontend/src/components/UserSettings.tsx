import React from 'react';
import NightMode from './NightMode';
import UserLanguage from './UserLanguage';
import ChangeUserCredentials from './ChangeUserCredentials';
import SignOut from './SignOut';
import { ChangeUserCredentialsTypes, changePasswordText, changeUsernameText } from '../utils/variables';

const UserSettings = () => (
    <div className='user-settings'>
        <NightMode />
        <UserLanguage />
        <ChangeUserCredentials type={ChangeUserCredentialsTypes.USERNAME} placeholder={changeUsernameText} classType="username" />
        <ChangeUserCredentials type={ChangeUserCredentialsTypes.USERPASSWORD} placeholder={changePasswordText} classType="password" />
        <SignOut />
    </div>
)

export default UserSettings;