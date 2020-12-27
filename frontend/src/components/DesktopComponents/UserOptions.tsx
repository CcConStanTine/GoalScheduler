import React, { useContext } from 'react';
import { LanguageContext } from '../../authentication/LanguageContext';
import languagePack from '../../utils/languagePack';
import ChangeEmail from '../../templates/pages/mobile/ChangeEmailPage';
import ChangePassword from '../../templates/pages/mobile/ChangePasswordPage';
import ChangeUsername from '../../templates/pages/mobile/ChangeUsernamePage';
import ChangeUserPhoto from '../../templates/pages/mobile/ChangeUserPhoto';
import renderDetails from './renderUserQuestionOrOptions';

const UserOptions = (): JSX.Element => {
    const { language } = useContext(LanguageContext);

    const options = [
        { title: languagePack[language].SETTINGS.changeUsername, answer: <ChangeUsername mobile={false} /> },
        { title: languagePack[language].SETTINGS.changeEmail, answer: <ChangeEmail mobile={false} /> },
        { title: languagePack[language].SETTINGS.changeUserPhoto, answer: <ChangeUserPhoto mobile={false} /> },
        { title: languagePack[language].SETTINGS.changePassword, answer: <ChangePassword mobile={false} /> },
    ];

    return (
        <div className='user-options'>
            {renderDetails(options)}
        </div>
    )
}

export default UserOptions;