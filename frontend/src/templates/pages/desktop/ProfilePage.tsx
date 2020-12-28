import React, { useContext } from 'react';
import { LanguageContext } from '../../../authentication/LanguageContext';
import RenderHeader from '../../../components/desktop/RenderHeader';
import languagePack from '../../../utils/languagePack';
import UserOptions from '../../../components/desktop/UserOptions';
import UserCredentials from '../../../components/desktop/UserCredentials';

const ProfilePage = (): JSX.Element => {
    const { language } = useContext(LanguageContext);

    return (
        <section className='profile-page-desktop'>
            <RenderHeader header={languagePack[language].PROFILE.title} />
            <div className='content-container'>
                <UserCredentials />
                <UserOptions />
            </div>
        </section>
    )
}

export default ProfilePage;