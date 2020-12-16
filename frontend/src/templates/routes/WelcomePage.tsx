import React, { useState, useContext } from 'react';
import { ScreenSizes } from '../../utils/variables';
import { AppContext } from '../../authentication/AppContext';
import { Redirect } from 'react-router-dom';
import useWindowDimensions from '../../utils/windowDimensions';
import WelcomePageMobile from '../pages/mobile/WelcomePage';
import WelcomePageDesktop from '../pages/desktop/WelcomePage';

const WelcomePage = (): JSX.Element => {
    const [showRegisterPanel, setShowRegisterPanel] = useState<boolean>(false);
    const { userContext } = useContext(AppContext);
    const { width } = useWindowDimensions();

    if (userContext?.token) return <Redirect to="/app/home" />

    if (width < ScreenSizes.Mobile)
        return <WelcomePageMobile
            showRegisterPanel={showRegisterPanel}
            setShowRegisterPanel={setShowRegisterPanel}
        />

    return <WelcomePageDesktop />
}

export default WelcomePage;