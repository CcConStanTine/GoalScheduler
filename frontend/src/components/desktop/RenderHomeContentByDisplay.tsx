import React from 'react';
import { DesktopDisplayOptions } from '../../utils/enums';
import HomePage from '../../templates/pages/desktop/HomePageContent';
import HelpPage from '../../templates/pages/desktop/HelpPage';
import ProfilePage from '../../templates/pages/desktop/ProfilePage';
import SettingsPage from '../../templates/pages/desktop/SettingsPage';

interface DesktopMain {
    display: DesktopDisplayOptions;
}

const RenderHomeContentByDisplay = ({ display }: DesktopMain) => (
    <>
        {display === DesktopDisplayOptions.HOME && <HomePage />}
        {display === DesktopDisplayOptions.PROFILE && <ProfilePage />}
        {display === DesktopDisplayOptions.HELP && <HelpPage />}
        {display === DesktopDisplayOptions.SETTINGS && <SettingsPage />}
    </>
);

export default RenderHomeContentByDisplay;