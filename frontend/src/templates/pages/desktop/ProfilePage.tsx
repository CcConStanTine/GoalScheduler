import React, { useContext, useState } from 'react';
import { LanguageContext } from '../../../authentication/LanguageContext';
import RenderHeader from '../../../components/DesktopComponents/RenderHeader';
import languagePack from '../../../utils/languagePack';
import { UserOptionsEnum } from '../../../utils/interfaces';
import UserOptions from '../../../components/DesktopComponents/UserOptions';
import UserCredentials from '../../../components/DesktopComponents/UserCredentials';

const ProfilePage = () => {
    const { language } = useContext(LanguageContext);
    const [activeOption, setActiveOption] = useState<UserOptionsEnum>(UserOptionsEnum.DEFAULT);

    const handleClick = (event: any, option: UserOptionsEnum) => {
        event.stopPropagation();
        setActiveOption(option);
    }

    return (
        <section className='profile-page-desktop' onClick={() => setActiveOption(UserOptionsEnum.DEFAULT)}>
            <RenderHeader header={languagePack[language].PROFILE.title} />
            <div className='content-container'>
                <UserCredentials />
                <UserOptions activeOption={activeOption} handleClick={handleClick} />
            </div>
        </section>
    )
}

export default ProfilePage;