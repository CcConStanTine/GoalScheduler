import React, { useEffect, useContext, useState } from 'react';
import { AppContext } from '../../../authentication/AppContext';
import { LanguageContext } from '../../../authentication/LanguageContext';
import DataRequests from '../../../authentication/DataRequests';
import { PlanTypes } from '../../../utils/variables';
import { DesktopDisplayOptions } from '../../../utils/interfaces';
import MainContent from '../../../components/DesktopMainContent';
import DesktopMenu from '../../../components/DesktopMenu';

const HomePage = () => {
    const { setLoggedIn } = useContext(AppContext);
    const { language } = useContext(LanguageContext);
    const [todayPlans, setTodayPlans] = useState([]);
    const [display, setDisplay] = useState<DesktopDisplayOptions>(DesktopDisplayOptions.HOME);

    useEffect(() => {
        const { token } = DataRequests.getCurrentUser();

        const updateUserInfo = async (): Promise<void> => {
            const userInfo = await DataRequests.getCurrentUserInfo();
            const { fileUrl: userPhoto } = await DataRequests.getUserPhoto();

            setLoggedIn && setLoggedIn({ ...userInfo, token, userPhoto })
        }

        const updateTodayPlans = async (): Promise<void> => {
            const plans = await DataRequests.getTypePlans(PlanTypes.DAY);
            setTodayPlans(plans);
        }

        updateUserInfo();
        updateTodayPlans();
    }, [setLoggedIn])
    return (
        <div className='home-page-desktop'>
            <DesktopMenu onClick={setDisplay} />
            <MainContent display={display} />
        </div>
    )
}

export default HomePage;