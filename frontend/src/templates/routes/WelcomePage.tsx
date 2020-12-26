import React, { useState, useContext } from 'react';
import { Redirect } from 'react-router-dom';
import { AppContext } from '../../authentication/AppContext';
import { ScreenSizes } from '../../utils/enums';
import { AppContextInterface } from '../../utils/interfaces';
import useWindowDimensions from '../../utils/windowDimensions';
import WelcomePageMobile from '../pages/mobile/WelcomePage';
import WelcomePageDesktop from '../pages/desktop/WelcomePage';

const WelcomePage = (): JSX.Element => {
    const [showRegisterPanel, setShowRegisterPanel] = useState<boolean>(false);
    const { userContext } = useContext<AppContextInterface>(AppContext);
    const { width } = useWindowDimensions();

    if (userContext?.token) return <Redirect to="/app/home" />

    return width >= ScreenSizes.MOBILE ?
        <WelcomePageDesktop />
        :
        <WelcomePageMobile showRegisterPanel={showRegisterPanel} setShowRegisterPanel={setShowRegisterPanel} />
}

export default WelcomePage;