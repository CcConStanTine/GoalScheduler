import React, { useState, useContext } from 'react';
import { ScreenSizes } from '../../utils/enums';
import { AppContext } from '../../authentication/AppContext';
import { Redirect } from 'react-router-dom';
import useWindowDimensions from '../../utils/windowDimensions';
import WelcomePageMobile from '../pages/mobile/WelcomePage';
import WelcomePageDesktop from '../pages/desktop/WelcomePage';
import { AppContextInterface } from '../../utils/interfaces';

const WelcomePage = (): JSX.Element => {
    const [showRegisterPanel, setShowRegisterPanel] = useState<boolean>(false);
    const { userContext } = useContext<AppContextInterface>(AppContext);
    const { width } = useWindowDimensions();

    if (userContext?.token) return <Redirect to="/app/home" />

    return width >= ScreenSizes.Mobile ?
        <WelcomePageDesktop />
        :
        <WelcomePageMobile showRegisterPanel={showRegisterPanel} setShowRegisterPanel={setShowRegisterPanel} />
}

export default WelcomePage;