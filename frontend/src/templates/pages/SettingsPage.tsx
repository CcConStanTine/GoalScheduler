import React from 'react';
import { settingsNavigationText, PageNavigationTypes } from '../../utils/variables';
import NavigationBar from '../../components/NavigationBar';
import UserData from '../../components/UserData';
import UserSettings from '../../components/UserSettings';

interface HomeInterface {
    history: any
}

const SettingsPage = ({ history }: HomeInterface) =>
    <section className='settings-page'>
        <NavigationBar type={PageNavigationTypes.DEFAULT} history={history} placeholder={settingsNavigationText} />
        <UserData />
        <UserSettings />
    </section>

export default SettingsPage;