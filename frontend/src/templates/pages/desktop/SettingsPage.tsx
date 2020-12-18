import React, { useContext } from 'react';
import languagePack from '../../../utils/languagePack';
import { LanguageContext } from '../../../authentication/LanguageContext';
import UserLanguage from '../../../components/DesktopComponents/UserLanguage';
import RenderHeader from '../../../components/DesktopComponents/RenderHeader';
import NightMode from '../../../components/DesktopComponents/NightMode';

const SettingsPage = () => {
    const { language } = useContext(LanguageContext);

    return (
        <section className='settings-page-desktop'>
            <RenderHeader header={languagePack[language].SETTINGS.title} />
            <UserLanguage />
            <NightMode />
        </section>
    )
}
export default SettingsPage;