import React, { useContext } from 'react';
import { languagePack, PageNavigationTypes } from '../../utils/variables';
import NavigationBar from '../../components/NavigationBar';
import UserData from '../../components/UserData';
import UserSettings from '../../components/UserSettings';
import { LanguageContext } from '../../authentication/LanguageContext';

interface HomeInterface {
    history: any
}

const SettingsPage = ({ history }: HomeInterface) => {
    const { language } = useContext(LanguageContext);

    return (
        <section className='settings-page'>
            <NavigationBar type={PageNavigationTypes.DEFAULT} history={history} placeholder={languagePack[language].settingsNavigationText} />
            <UserData />
            <UserSettings />
        </section>
    )
}

export default SettingsPage;