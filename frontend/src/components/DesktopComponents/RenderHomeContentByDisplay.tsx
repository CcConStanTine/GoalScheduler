import React from 'react';
import { DesktopDisplayOptions } from '../../utils/interfaces';
import HomePage from '../../templates/pages/desktop/HomePageContent';
import PasswordPage from '../../templates/pages/desktop/PasswordPage';
import HelpPage from '../../templates/pages/desktop/HelpPage';
import MessagesPage from '../../templates/pages/desktop/MessagesPage';
import ProfilePage from '../../templates/pages/desktop/ProfilePage';
import SettingsPage from '../../templates/pages/desktop/SettingsPage';

interface DesktopMain {
    display: DesktopDisplayOptions;
}

const RenderHomeContentByDisplay = ({ display }: DesktopMain) => (
    <>
        {display === DesktopDisplayOptions.HOME && <HomePage />}
        {display === DesktopDisplayOptions.PROFILE && <ProfilePage />}
        {display === DesktopDisplayOptions.MESSAGES && <MessagesPage />}
        {display === DesktopDisplayOptions.HELP && <HelpPage />}
        {display === DesktopDisplayOptions.SETTINGS && <SettingsPage />}
        {display === DesktopDisplayOptions.PASSWORD && <PasswordPage />}
    </>
);

export default RenderHomeContentByDisplay;