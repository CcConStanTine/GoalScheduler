import React, { useContext } from 'react';
import { LanguageContext } from '../../authentication/LanguageContext';
import languagePack from '../../utils/languagePack';
import ChangeEmail from './ChangeEmail';
import ChangePassword from './ChangePassword';
import ChangeUsername from './ChangeUsername';
import ChangeUserPhoto from './ChangeUserPhoto';
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
                {activeOption === UserOptionsEnum.USERNAME && <ChangeUsername />}
            </div>
            <div className='entry-container' onClick={event => handleClick(event, UserOptionsEnum.EMAIL)}>
                <h1>{languagePack[language].SETTINGS.changeEmail}</h1>
                {activeOption === UserOptionsEnum.EMAIL && <ChangeEmail />}
            </div>
            <div className='entry-container' onClick={event => handleClick(event, UserOptionsEnum.USERPHOTO)}>
                <h1>{languagePack[language].SETTINGS.changeUserPhoto}</h1>
                {activeOption === UserOptionsEnum.USERPHOTO && <ChangeUserPhoto />}
            </div>
            <div className='entry-container' onClick={event => handleClick(event, UserOptionsEnum.PASSWORD)}>
                <h1>{languagePack[language].SETTINGS.changePassword}</h1>
                {activeOption === UserOptionsEnum.PASSWORD && <ChangePassword />}
            </div>
        </div>
    )
}

export default UserOptions;