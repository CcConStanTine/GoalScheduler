import React, { useContext } from 'react';
import { PageNavigationTypes } from '../../../utils/variables';
import languagePack from '../../../utils/languagePack';
import NavigationBar from '../../../components/mobile/NavigationBar';
import UserData from '../../../components/mobile/UserData';
import UserSettings from '../../../components/mobile/UserSettings';
import { LanguageContext } from '../../../authentication/LanguageContext';

const SettingsPage = (): JSX.Element => {
    const { language } = useContext(LanguageContext);

    return (
        <section className='settings-page'>
            <NavigationBar type={PageNavigationTypes.DEFAULT} placeholder={languagePack[language].SETTINGS.title} />
            <UserData />
            <UserSettings />
        </section>
    )
}

export default SettingsPage;