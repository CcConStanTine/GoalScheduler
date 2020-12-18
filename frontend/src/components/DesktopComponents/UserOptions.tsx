import React, { useContext } from 'react';
import { LanguageContext } from '../../authentication/LanguageContext';
import languagePack from '../../utils/languagePack';
import ChangeEmail from '../../templates/pages/mobile/ChangeEmailPage';
import ChangePassword from '../../templates/pages/mobile/ChangePasswordPage';
import ChangeUsername from '../../templates/pages/mobile/ChangeUsernamePage';
import ChangeUserPhoto from '../../templates/pages/mobile/ChangeUserPhoto';
import { UserOptionsEnum } from '../../utils/interfaces';

interface Options {
    activeOption: UserOptionsEnum;
    handleClick: any;
}

const UserOptions = ({ handleClick, activeOption }: Options) => {
    const { language } = useContext(LanguageContext);

    return (
        <div className='user-options'>
            <div className='entry-container' onClick={event => handleClick(event, UserOptionsEnum.USERNAME)}>
                <h1>{languagePack[language].SETTINGS.changeUsername}</h1>
                {activeOption === UserOptionsEnum.USERNAME && <ChangeUsername mobile={false} />}
            </div>
            <div className='entry-container' onClick={event => handleClick(event, UserOptionsEnum.EMAIL)}>
                <h1>{languagePack[language].SETTINGS.changeEmail}</h1>
                {activeOption === UserOptionsEnum.EMAIL && <ChangeEmail mobile={false} />}
            </div>
            <div className='entry-container' onClick={event => handleClick(event, UserOptionsEnum.USERPHOTO)}>
                <h1>{languagePack[language].SETTINGS.changeUserPhoto}</h1>
                {activeOption === UserOptionsEnum.USERPHOTO && <ChangeUserPhoto mobile={false} />}
            </div>
            <div className='entry-container' onClick={event => handleClick(event, UserOptionsEnum.PASSWORD)}>
                <h1>{languagePack[language].SETTINGS.changePassword}</h1>
                {activeOption === UserOptionsEnum.PASSWORD && <ChangePassword mobile={false} />}
            </div>
        </div>
    )
}

export default UserOptions;