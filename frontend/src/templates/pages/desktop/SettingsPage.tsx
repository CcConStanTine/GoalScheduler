import React, { useContext } from 'react';
import languagePack from '../../../utils/languagePack';
import { LanguageContext } from '../../../authentication/LanguageContext';
import UserLanguage from '../../../components/desktop/UserLanguage';
import RenderHeader from '../../../components/desktop/RenderHeader';
import NightMode from '../../../components/desktop/NightMode';

const SettingsPage = (): JSX.Element => {
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